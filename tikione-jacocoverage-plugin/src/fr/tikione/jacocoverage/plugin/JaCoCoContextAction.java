package fr.tikione.jacocoverage.plugin;

import fr.tikione.jacocoexec.analyzer.JaCoCoReportAnalyzer;
import fr.tikione.jacocoexec.analyzer.JaCoCoXmlReportParser;
import fr.tikione.jacocoexec.analyzer.JavaClass;
import static fr.tikione.jacocoverage.plugin.Utils.getProjectId;
import fr.tikione.jacocoverage.plugin.anno.AbstractCoverageAnnotation;
import fr.tikione.jacocoverage.plugin.config.Globals;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.tools.ant.module.api.AntProjectCookie;
import org.apache.tools.ant.module.api.AntTargetExecutor;
import org.netbeans.api.project.Project;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.DynamicMenuContent;
import org.openide.execution.ExecutorTask;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbPreferences;
import org.xml.sax.SAXException;

/**
 * A toolkit that launches Ant tasks with the JaCoCo JavaAgent, colorizes Java source files and shows a coverage report.
 *
 * @author Jonathan Lermitage
 */
public abstract class JaCoCoContextAction extends AbstractAction {

    private static final long serialVersionUID = 1L;

    /** The project the contextual action is called from. */
    private final Project project;

    /** Additional properties passed to the Ant task. */
    private final Properties addAntTargetProps = new Properties();

    /** The Ant task to launch. */
    private final String antTask;

    /**
     * Enable the context action on supported projects only.
     *
     * @param context the context the action is called from.
     * @param project the project the contextual action is called from.
     * @param antTask additional properties passed to the Ant task.
     */
    public JaCoCoContextAction(Lookup context, Project project, String antTask) {
        this.project = project;
        this.antTask = antTask;
        setEnabled(Utils.isProjectSupported(project));
        putValue(DynamicMenuContent.HIDE_WHEN_DISABLED, false);
    }

    public @Override
    void actionPerformed(ActionEvent ae) {
        try {
            // Retrieve JaCoCoverage preferences. They contains coloration and JaCoCo JavaAgent customizations.
            Preferences pref = NbPreferences.forModule(JaCoCoContextAction.class);
            final boolean enblHighlight = pref.getBoolean(Globals.PROP_ENABLE_HIGHLIGHT, Globals.DEF_ENABLE_HIGHLIGHT);
            final boolean enblConsoleReport = pref.getBoolean(Globals.PROP_ENABLE_CONSOLE_REPORT, Globals.DEF_ENABLE_CONSOLE_REPORT);

            if (enblHighlight || enblConsoleReport) {
                // Retrieve project properties.
                FileObject prjPropsFo = project.getProjectDirectory().getFileObject("nbproject/project.properties");
                final Properties prjProps = new Properties();
                prjProps.load(prjPropsFo.getInputStream());

                final File xmlreport = Utils.getJacocoXmlReportfile(project);
                final File binreport = Utils.getJacocoBinReportFile(project);
                if (binreport.exists() && !binreport.delete() || xmlreport.exists() && !xmlreport.delete()) {
                    String msg = "Cannot delete the previous JaCoCo report files, please delete them manually:\n"
                            + binreport.getAbsolutePath() + " and/or\n"
                            + xmlreport.getAbsolutePath();
                    NotifyDescriptor nd = new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);
                    DialogDisplayer.getDefault().notify(nd);
                } else {
                    // Apply JaCoCo JavaAgent customization.
                    String antTaskJavaagentParam = pref.get(Globals.PROP_TEST_ANT_TASK_JAVAAGENT, Globals.DEF_TEST_ANT_TASK_JAVAAGENT)
                            .replace("{pathOfJacocoagentJar}", Utils.getJacocoAgentJar().getAbsolutePath())
                            .replace("{appPackages}", Utils.getProjectJavaPackagesAsStr(project, prjProps, ":", ".*"));

                    FileObject scriptToExecute = project.getProjectDirectory().getFileObject("build", "xml");
                    DataObject dataObj = DataObject.find(scriptToExecute);
                    AntProjectCookie antCookie = dataObj.getLookup().lookup(AntProjectCookie.class);

                    AntTargetExecutor.Env env = new AntTargetExecutor.Env();
                    AntTargetExecutor executor = AntTargetExecutor.createTargetExecutor(env);

                    // Add the customized JaCoCo JavaAgent to the JVM arguments given to the Ant task. The JaCoCo JavaAgent is
                    // appended to the existing list of JVM arguments that is given to the Ant task.
                    Properties targetProps = env.getProperties();
                    targetProps.putAll(addAntTargetProps);
                    String prjJvmArgs = Utils.getProperty(prjProps, "run.jvmargs");
                    targetProps.put("run.jvmargs", prjJvmArgs + "  -javaagent:" + antTaskJavaagentParam);
                    env.setProperties(targetProps);

                    // Launch the Ant task with the JaCoCo JavaAgent.
                    final ExecutorTask execute = executor.execute(antCookie, new String[]{antTask});

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // Wait for the end of the Ant task execution. We do it in a new thread otherwise it would
                                // freeze the current one. This is a workaround for a known and old NetBeans bug: the ExecutorTask
                                // object provided by the NetBeans platform is not correctly wrapped.
                                if (0 == execute.result() && binreport.exists()) {
                                    // Load the generated JaCoCo coverage report.
                                    String prjDir = Utils.getProjectDir(project) + File.separator;
                                    File classDir = new File(prjDir + Utils.getProperty(prjProps, "build.classes.dir") + File.separator);
                                    File srcDir = new File(prjDir + Utils.getProperty(prjProps, "src.dir") + File.separator);
                                    JaCoCoReportAnalyzer.toXmlReport(binreport, xmlreport, classDir, srcDir);
                                    final Map<String, JavaClass> coverageData = JaCoCoXmlReportParser.getCoverageData(xmlreport);

                                    // Remove existing highlighting (from a previous coverage task), show a report in the NetBeans
                                    // console and apply highlighting on each Java source file.
                                    AbstractCoverageAnnotation.removeAll(getProjectId(project));
                                    if (enblConsoleReport) {
                                        JaCoCoReportAnalyzer.toConsoleReport(coverageData, Globals.TXTREPORT_TABNAME);
                                    }
                                    if (enblHighlight) {
                                        for (final JavaClass jclass : coverageData.values()) {
                                            Utils.colorDoc(project, jclass);
                                        }
                                    }
                                    xmlreport.delete();
                                    binreport.delete();
                                } else {
                                    AbstractCoverageAnnotation.removeAll(getProjectId(project));
                                    Utils.closeJaCoCoConsoleReport(Globals.TXTREPORT_TABNAME);
                                }
                            } catch (FileNotFoundException ex) {
                                Exceptions.printStackTrace(ex);
                            } catch (IOException ex) {
                                Exceptions.printStackTrace(ex);
                            } catch (ParserConfigurationException ex) {
                                Exceptions.printStackTrace(ex);
                            } catch (SAXException ex) {
                                Exceptions.printStackTrace(ex);
                            }
                        }
                    }).start();
                }
            } else {
                String msg = "Please enable at least one JaCoCoverage feature first (highlighting or reporting).";
                NotifyDescriptor nd = new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);
                DialogDisplayer.getDefault().notify(nd);
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IllegalArgumentException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public Project getProject() {
        return project;
    }

    public Properties getAddAntTargetProps() {
        return addAntTargetProps;
    }
}
