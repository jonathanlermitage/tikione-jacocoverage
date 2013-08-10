package fr.tikione.jacocoverage.plugin.util;

import fr.tikione.jacocoexec.analyzer.JavaClass;
import fr.tikione.jacocoverage.plugin.anno.AbstractCoverageAnnotation;
import fr.tikione.jacocoverage.plugin.anno.CoverageAnnotation;
import fr.tikione.jacocoverage.plugin.anno.CoverageGlyphedAnnotation;
import fr.tikione.jacocoverage.plugin.anno.EditorCoverageStateEnum;
import fr.tikione.jacocoverage.plugin.config.Config;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import javax.swing.text.StyledDocument;
import org.netbeans.api.java.classpath.GlobalPathRegistry;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.openide.awt.HtmlBrowser;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.modules.InstalledFileLocator;
import org.openide.nodes.Node;
import org.openide.text.Line;
import org.openide.text.NbDocument;
import org.openide.util.Exceptions;
import org.openide.util.Utilities;
import org.openide.windows.IOProvider;

/**
 * Some NetBeans related utilities.
 *
 * @author Jonathan Lermitage
 */
public class NBUtils {

    private NBUtils() {
    }

    /**
     * Close a NetBeans console tab.
     *
     * @param tabName the name on the tab.
     * @throws IOException if an I/O error occurs.
     */
    public static void closeConsoleTab(String tabName)
            throws IOException {
        IOProvider.getDefault().getIO(tabName, false).closeInputOutput();
    }

    /**
     * Color (in editor) all the document representing the Java class.
     *
     * @param project the project containing the Java class.
     * @param jclass the Java class informations and coverage data.
     * @param multiLnInst enable coloring of multi-lines instructions.
     * @param srcDir the folder containing Java sources. Needed only if {@code multiLnInst} is true, otherwise you can use {@code null}.
     */
    @SuppressWarnings("AssignmentToForLoopParameter")
    public static void colorDoc(Project project, JavaClass jclass, boolean multiLnInst, File srcDir) {
        String classResource = jclass.getPackageName() + jclass.getClassName();
        String prjId = getProjectId(project);
        int theme = Config.getTheme();
        FIND_JAVA_FO:
        for (FileObject curRoot : GlobalPathRegistry.getDefault().getSourceRoots()) {
            FileObject fileObject = curRoot.getFileObject(classResource);
            if (fileObject != null && "java".equalsIgnoreCase(fileObject.getExt())) {
                try {
                    DataObject dataObject = DataObject.find(fileObject);
                    Node node = dataObject.getNodeDelegate();
                    EditorCookie editorCookie = node.getLookup().lookup(EditorCookie.class);
                    Map<Integer, fr.tikione.jacocoexec.analyzer.CoverageStateEnum> coverage = jclass.getCoverage();
                    Map<Integer, String> coverageDesc = jclass.getCoverageDesc();
                    if (editorCookie != null) {
                        StyledDocument doc = editorCookie.openDocument();
                        if (doc != null) {
                            int startLine = 0;
                            int endLine = NbDocument.findLineNumber(doc, doc.getLength());
                            Line.Set lineset = editorCookie.getLineSet();
                            for (int covIdx : coverage.keySet()) {
                                if (covIdx >= startLine && covIdx <= endLine) {
                                    Line line = lineset.getOriginal(covIdx);
                                    EditorCoverageStateEnum coverageState;
                                    switch (coverage.get(covIdx)) {
                                        case COVERED:
                                            coverageState = EditorCoverageStateEnum.COVERED;
                                            break;
                                        case NOT_COVERED:
                                            coverageState = EditorCoverageStateEnum.NOT_COVERED;
                                            break;
                                        case PARTIALLY_COVERED:
                                            coverageState = EditorCoverageStateEnum.PARTIALLY_COVERED;
                                            break;
                                        default:
                                            coverageState = EditorCoverageStateEnum.COVERED;
                                    }
                                    AbstractCoverageAnnotation annotation;
                                    if (coverageDesc.containsKey(covIdx)) {
                                        annotation = new CoverageGlyphedAnnotation(
                                                coverageState,
                                                prjId,
                                                jclass.getPackageName() + jclass.getClassName(),
                                                covIdx,
                                                coverageDesc.get(covIdx),
                                                theme);
                                    } else {
                                        annotation = new CoverageAnnotation(
                                                coverageState,
                                                prjId,
                                                jclass.getPackageName() + jclass.getClassName(),
                                                covIdx,
                                                theme);
                                    }
                                    annotation.attach(line);
                                    line.addPropertyChangeListener(annotation);
                                }
                            }
                            if (multiLnInst) {
                                File javafile = new File(srcDir, jclass.getPackageName() + jclass.getClassName());
                                List<String> javalines = org.apache.commons.io.FileUtils.readLines(javafile);
                                int nblines = javalines.size();
                                for (int lineIdx = 0; lineIdx < nblines; lineIdx++) {
                                    boolean isCovered = coverage.containsKey(lineIdx);
                                    boolean isCoveredDesc = coverageDesc.containsKey(lineIdx);
                                    if ((isCovered || isCoveredDesc)
                                            && (lineIdx + 1 < nblines)
                                            && (!coverage.containsKey(lineIdx + 1) && !coverageDesc.containsKey(lineIdx + 1))
                                            && (!Utils.isIntructionFinished(javalines.get(lineIdx)))) {
                                        EditorCoverageStateEnum coverageState;
                                        switch (coverage.get(lineIdx)) {
                                            case COVERED:
                                                coverageState = EditorCoverageStateEnum.COVERED;
                                                break;
                                            case NOT_COVERED:
                                                coverageState = EditorCoverageStateEnum.NOT_COVERED;
                                                break;
                                            case PARTIALLY_COVERED:
                                                coverageState = EditorCoverageStateEnum.PARTIALLY_COVERED;
                                                break;
                                            default:
                                                coverageState = EditorCoverageStateEnum.COVERED;
                                        }
                                        coverage.put(lineIdx + 1, coverage.get(lineIdx));
                                        AbstractCoverageAnnotation annotation = new CoverageAnnotation(
                                                coverageState,
                                                prjId,
                                                jclass.getPackageName() + jclass.getClassName(),
                                                lineIdx + 1,
                                                theme);
                                        Line line = lineset.getOriginal(lineIdx + 1);
                                        annotation.attach(line);
                                        line.addPropertyChangeListener(annotation);
                                    }
                                }
                            }
                        }
                        break FIND_JAVA_FO;
                    }
                } catch (DataObjectNotFoundException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }

    /**
     * launch the default browser to display an URL.
     *
     * @param url the URL to display.
     */
    public static void extBrowser(String url) {
        try {
            HtmlBrowser.URLDisplayer.getDefault().showURL(new URL(url));
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * Get the JaCoCo-Agent JAR file that is registered in the IDE.
     *
     * @return the JaCoCo-Agent JAR.
     */
    public static File getJacocoAgentJar() {
        return InstalledFileLocator.getDefault().locate("modules/ext/jacocoagent.jar", "fr.tikione.jacoco.lib", false);
    }

    /**
     * Get the JaCoCo-Ant JAR file that is registered in the IDE.
     *
     * @return the JaCoCo-Ant JAR.
     */
    public static File getJacocoAntJar() {
        return InstalledFileLocator.getDefault().locate("modules/ext/jacocoant.jar", "fr.tikione.jacoco.lib", false);
    }

    /**
     * Get the full path of the directory containing a given project.
     *
     * @param project the project.
     * @return the directory containing the project.
     */
    public static String getProjectDir(Project project) {
        return FileUtil.getFileDisplayName(project.getProjectDirectory());
    }

    /**
     * Generate a string representing the project. Two different projects should have different representation.
     *
     * @param project the project.
     * @return a representation of the project.
     */
    public static String getProjectId(Project project) {
        return getProjectDir(project) + '_' + project.toString();
    }

    /**
     * Retrieve the list of Java packages of a given project.
     * Each element is a fully qualified package name, e.g. <code>foo</code>, <code>foo.bar</code> and <code>foo.bar.too</code>.
     *
     * @param project the project to list Java packages.
     * @param prjProps the project properties.
     * @return a list of Java package names.
     */
    public static List<String> getProjectJavaPackages(Project project, Properties prjProps) {
        List<String> packages = new ArrayList<>(8);
        String srcFolderName = Utils.getProperty(prjProps, "src.dir");
        List<File> packagesAsFolders = Utils.listFolders(
                new File(NBUtils.getProjectDir(project) + File.separator + srcFolderName + File.separator));
        int rootDirnameLen = NBUtils.getProjectDir(project).length() + srcFolderName.length() + 2;
        for (File srcPackage : packagesAsFolders) {
            packages.add(srcPackage.getAbsolutePath()
                    .substring(rootDirnameLen)
                    .replaceAll(Matcher.quoteReplacement(File.separator), "."));
        }
        return packages;
    }

    /**
     * Retrieve the list of Java packages of a given project.
     * Each element is a fully qualified package name, e.g.  <code>foo</code>, <code>foo.bar</code> and <code>foo.bar.too</code>.
     * Elements are separated with a given separator string.
     *
     * @param project the project to list Java packages.
     * @param prjProps the project properties.
     * @param separator the separator string.
     * @param prefix a prefix to append to the end of each package name (can be empty).
     * @return a list of Java package names.
     */
    public static String getProjectJavaPackagesAsStr(Project project, Properties prjProps, String separator, String prefix) {
        List<String> packagesList = getProjectJavaPackages(project, prjProps);
        StringBuilder packages = new StringBuilder(256);
        if (packagesList.isEmpty()) {
            packages.append("*");
        } else {
            boolean first = true;
            for (String pack : packagesList) {
                if (!first) {
                    packages.append(separator);
                }
                packages.append(pack).append(prefix);
                first = false;
            }
        }
        return packages.toString();
    }

    /**
     * Get the name of a project.
     *
     * @param project the project.
     * @return the project's name.
     */
    public static String getProjectName(Project project) {
        return ProjectUtils.getInformation(project).getName();
    }

    /**
     * Get the selected project. Return null if multiple projects are selected.
     *
     * @return the selected project or null if none or many.
     */
    public static Project getSelectedProject() {
        Project project;
        Collection<? extends Project> prjs = getAllSelectedProjects();
        if (prjs.size() == 1) {
            project = prjs.iterator().next();
        } else {
            project = null;
        }
        return project;
    }

    /**
     * Get every selected projects.
     *
     * @return the selected projects.
     */
    public static Collection<? extends Project> getAllSelectedProjects() {
        return Utilities.actionsGlobalContext().lookupAll(Project.class);
    }
}
