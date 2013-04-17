package fr.tikione.jacocoverage.plugin;

import fr.tikione.jacocoverage.plugin.config.Globals;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.apache.tools.ant.module.api.AntProjectCookie;
import org.apache.tools.ant.module.api.AntTargetExecutor;
import org.netbeans.api.project.Project;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.DynamicMenuContent;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.ContextAwareAction;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbPreferences;

/**
 * The "Test with JaCoCoverage" contextual action registration.
 * Start an Ant task with the JaCoCo JavaAgent correctly configured, colorize Java source files and show a coverage report.
 *
 * @author Jonathan Lermitage
 */
@ActionID(category = "Project",
          id = "fr.tikione.jacocoverage.plugin.RunWithJaCoCoAction")
@ActionRegistration(displayName = "Test with JaCoCoverage",
                    lazy = false)
@ActionReference(path = "Projects/Actions",
                 position = 1984,
                 separatorBefore = 1983,
                 separatorAfter = 1985)
public final class RunWithJaCoCoAction extends AbstractAction implements ContextAwareAction {

    private static final long serialVersionUID = 1L;

    public @Override
    void actionPerformed(ActionEvent e) {
    }

    public @Override
    Action createContextAwareInstance(Lookup context) {
        return new ContextAction(context);
    }

    /**
     * The "Test with JaCoCoverage" contextual action.
     */
    private static final class ContextAction extends AbstractAction {

        private static final long serialVersionUID = 1L;

        /** The project the contextual action is called from. */
        private final Project project;

        /**
         * Enable the context action on supported projects only.
         *
         * @param context the context the action is called from.
         */
        public ContextAction(Lookup context) {
            project = context.lookup(Project.class);
            setEnabled(Utils.isProjectSupported(project));
            putValue(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
            putValue(NAME, "Test with JaCoCoverage");
        }

        public @Override
        void actionPerformed(ActionEvent ae) {
            try {
                // Retrieve JaCoCoverage preferences. They contains coloration and JaCoCo JavaAgent customizations.
                Preferences pref = NbPreferences.forModule(RunWithJaCoCoAction.class);
                String antTask = pref.get(Globals.PROP_TEST_ANT_TASK, Globals.DEF_TEST_ANT_TASK);

                // Retrieve proejct properties.
                FileObject projectPropertiesFile = project.getProjectDirectory().getFileObject("nbproject/project.properties");
                Properties projectProperties = new Properties();
                projectProperties.load(projectPropertiesFile.getInputStream());

                File jacocoExecFile = Utils.getJacocoexec(project);
                if (jacocoExecFile.exists() && !jacocoExecFile.delete()) {
                    String msg = "Cannot delete the previous JaCoCo report file.\n"
                            + "Please delete it manually: \"" + jacocoExecFile.getAbsolutePath() + "\".";
                    NotifyDescriptor nd = new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);
                    DialogDisplayer.getDefault().notify(nd);
                } else {
                    // Apply JaCoCo JavaAgent customization.
                    String antTaskJavaagentParam = pref.get(Globals.PROP_TEST_ANT_TASK_JAVAAGENT, Globals.DEF_TEST_ANT_TASK_JAVAAGENT)
                            .replace("{pathOfJacocoagentJar}", Utils.getJacocoAgentJar().getAbsolutePath())
                            .replace("{appPackages}", Utils.getProjectJavaPackagesAsStr(project, projectProperties, ":", ".*"));

                    FileObject scriptToExecute = project.getProjectDirectory().getFileObject("build", "xml");
                    DataObject dataObj = DataObject.find(scriptToExecute);
                    AntProjectCookie antCookie = dataObj.getLookup().lookup(AntProjectCookie.class);

                    AntTargetExecutor.Env env = new AntTargetExecutor.Env();
                    AntTargetExecutor executor = AntTargetExecutor.createTargetExecutor(env);

                    // Add the customized JaCoCo JavaAgent to the JVM arguments given to the Ant task. The JaCoCo JavaAgent is
                    // appended to the existing list of JVM arguments that is given to the Ant task.
                    Properties targetProps = env.getProperties();    // Properties that will be given to the Ant task.
                    String projectJvmArgs = projectProperties.getProperty("run.jvmargs", "");
                    targetProps.put("run.jvmargs", projectJvmArgs + "  -javaagent:" + antTaskJavaagentParam);
                    env.setProperties(targetProps);

                    // Launch the Ant task.
                    executor.execute(antCookie, new String[]{antTask});
                }
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IllegalArgumentException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }
}
