package fr.tikione.jacocoverage.plugin;

import fr.tikione.jacocoexec.analyzer.JavaClass;
import fr.tikione.jacocoverage.plugin.anno.AbstractCoverageAnnotation;
import fr.tikione.jacocoverage.plugin.anno.CoverageAnnotation;
import fr.tikione.jacocoverage.plugin.anno.CoverageStateEnum;
import java.awt.Desktop;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.StyledDocument;
import org.netbeans.api.java.classpath.GlobalPathRegistry;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
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
import org.openide.windows.IOProvider;

/**
 * Some NetBeans related utilities.
 *
 * @author Jonathan Lermitage
 */
public class Utils {

    /** Regex to recognize "${key}" patterns in Properties files used by NetBeans projects. */
    private static final String PA_NBPROPKEY_SHORTCUT = "\\$\\{([^\\}]+)\\}";

    /** Compiled regex to recognize "${key}" patterns in Properties files used by NetBeans projects. */
    private static final Pattern CPA_NBPROPKEY_SHORTCUT = Pattern.compile(PA_NBPROPKEY_SHORTCUT);

    private Utils() {
    }

    /**
     * Launche the default browser to display an URI.
     *
     * @param uri the URI to display.
     */
    public static void extBrowser(String uri) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(uri));
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            } catch (URISyntaxException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    /**
     * Close a NetBeans console tab.
     *
     * @param tabName the name on the tab.
     */
    public static void closeJaCoCoConsoleReport(String tabName)
            throws IOException {
        IOProvider.getDefault().getIO(tabName, false).closeInputOutput();
    }

    /**
     * Check a regular expression.
     *
     * @param src the text to examine.
     * @param pattern the compiled regular expression, targeted area are enclosed with parenthesis.
     * @return <code>true</code> if the regular expression is checked, otherwise <code>false</code>.
     */
    public static boolean checkRegex(String src, Pattern pattern) {
        return pattern.matcher(src).find();
    }

    /**
     * Return the capturing groups from the regular expression in the string.
     *
     * @param src the string to search in.
     * @param pattern the compiled pattern.
     * @param expectedNbMatchs the expected tokens matched, for performance purpose.
     * @return all the strings matched (in an ArrayList).
     */
    public static List<String> getGroupsFromRegex(String src, Pattern pattern, int expectedNbMatchs) {
        List<String> res = new ArrayList<String>(expectedNbMatchs);
        Matcher matcher = pattern.matcher(src);
        while (matcher.find()) {
            for (int group = 1; group <= matcher.groupCount(); group++) {
                res.add(matcher.group(group));
            }
        }
        return res;
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
     * Get the JaCoCo bnary report file of the given project.
     *
     * @param project the project to get JaCoCo report file.
     * @return the JaCoCo report file.
     */
    public static File getJacocoBinReportFile(Project project) {
        String jacocoExecPath = getProjectDir(project) + File.separator + "jacoco.exec";
        return new File(jacocoExecPath);
    }

    /**
     * Get the JaCoCo XML report file of the given project.
     *
     * @param project the project to get JaCoCo report file.
     * @return the JaCoCo report file.
     */
    public static File getJacocoXmlReportfile(Project project) {
        String jacocoXmlReportPath = getProjectDir(project) + File.separator + "jacocoverage.report.xml";
        return new File(jacocoXmlReportPath);
    }

    /**
     * Indicate if a project is supported by JaCoCoverage.
     * See http://wiki.netbeans.org/DevFaqActionAllAvailableProjectTypes for help.
     *
     * @param project the project.
     * @return true if supported, otherwise false.
     */
    public static boolean isProjectSupported(Project project) {
        boolean supported;
        String projectClass = project.getClass().getName();
        if (projectClass.equals("org.netbeans.modules.java.j2seproject.J2SEProject")
                || projectClass.equals("org.netbeans.modules.apisupport.project.NbModuleProject")) {
            supported = true;
        } else {
            supported = false;
        }
        return supported;
    }

    /**
     * Get a list of every subfolder contained in a given folder.
     *
     * @param root the root folder.
     * @return a list of subfolders.
     */
    public static List<File> listFolders(File root) {
        List<File> folders = new ArrayList<File>(16);
        File[] subfolders = root.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        folders.addAll(Arrays.asList(subfolders));
        for (File subfolder : subfolders) {
            folders.addAll(listFolders(subfolder));
        }
        return folders;
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
        List<String> packages = new ArrayList<String>(8);
        String srcFolderName = getProperty(prjProps, "src.dir");
        List<File> packagesAsFolders = listFolders(new File(getProjectDir(project) + File.separator + srcFolderName + File.separator));
        int rootDirnameLen = getProjectDir(project).length() + srcFolderName.length() + 2;
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
        StringBuilder packages = new StringBuilder(128);
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
     * Generate a string representing the project. Two different projects should have different representation.
     *
     * @param project the project.
     * @return a representation of the proejct.
     */
    public static String getProjectId(Project project) {
        return getProjectDir(project) + '_' + project.toString();
    }

    /**
     * Get a key value from a Properties object, with support of NetBeans key references (aka "${key}").
     *
     * @param props the Properties object to load key value from.
     * @param key the key value to get value.
     * @return the key value.
     */
    public static String getProperty(Properties props, String key) {
        String value = props.getProperty(key, "");
        int security = 0;
        while (security++ < 80 && checkRegex(value, CPA_NBPROPKEY_SHORTCUT)) {
            List<String> refs = getGroupsFromRegex(value, CPA_NBPROPKEY_SHORTCUT, 3);
            for (String ref : refs) {
                value = value.replaceFirst(PA_NBPROPKEY_SHORTCUT, props.getProperty(ref, ""));
            }
        }
        return value;
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
     * Get the name of a project.
     *
     * @param project the project.
     * @return the project's name.
     */
    public static String getProjectName(Project project) {
        return ProjectUtils.getInformation(project).getName();
    }

    /**
     * Color (in editor) all the document representing the Java class.
     *
     * @param project the project containing the Java class.
     * @param jclass the Java class informations and coverage data.
     */
    public static void colorDoc(Project project, JavaClass jclass) {
        String classResource = jclass.getPackageName() + jclass.getClassName();
        String prjId = getProjectId(project);
        FIND_JAVA_FO:
        for (FileObject curRoot : GlobalPathRegistry.getDefault().getSourceRoots()) {
            FileObject fileObject = curRoot.getFileObject(classResource);
            if (fileObject != null && fileObject.getExt().equalsIgnoreCase("JAVA")) {
                try {
                    DataObject dataObject = DataObject.find(fileObject);
                    Node node = dataObject.getNodeDelegate();
                    EditorCookie editorCookie = node.getLookup().lookup(EditorCookie.class);
                    if (editorCookie != null) {
                        StyledDocument doc = editorCookie.openDocument();
                        if (doc != null) {
                            int startLine = 0;
                            int endLine = NbDocument.findLineNumber(doc, doc.getLength());
                            Line.Set lineset = editorCookie.getLineSet();
                            for (int covIdx : jclass.getCoveredLines()) {
                                if (covIdx >= startLine && covIdx <= endLine) {
                                    Line line = lineset.getOriginal(covIdx);
                                    AbstractCoverageAnnotation annotation = new CoverageAnnotation(
                                            CoverageStateEnum.COVERED,
                                            prjId,
                                            jclass.getPackageName() + jclass.getClassName(),
                                            covIdx);
                                    annotation.attach(line);
                                    line.addPropertyChangeListener(annotation);
                                }
                            }
                            for (int covIdx : jclass.getPartiallyCoveredLines()) {
                                if (covIdx >= startLine && covIdx <= endLine) {
                                    Line line = lineset.getOriginal(covIdx);
                                    AbstractCoverageAnnotation annotation = new CoverageAnnotation(
                                            CoverageStateEnum.PARTIALLY_COVERED,
                                            prjId,
                                            jclass.getPackageName() + jclass.getClassName(),
                                            covIdx);
                                    annotation.attach(line);
                                    line.addPropertyChangeListener(annotation);
                                }
                            }
                            for (int covIdx : jclass.getNotCoveredLines()) {
                                if (covIdx >= startLine && covIdx <= endLine) {
                                    Line line = lineset.getOriginal(covIdx);
                                    AbstractCoverageAnnotation annotation = new CoverageAnnotation(
                                            CoverageStateEnum.NOT_COVERED,
                                            prjId,
                                            jclass.getPackageName() + jclass.getClassName(),
                                            covIdx);
                                    annotation.attach(line);
                                    line.addPropertyChangeListener(annotation);
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
}
