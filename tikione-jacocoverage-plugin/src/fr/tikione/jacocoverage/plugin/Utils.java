package fr.tikione.jacocoverage.plugin;

import fr.tikione.jacocoexec.analyzer.JavaClass;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import javax.swing.text.StyledDocument;
import org.netbeans.api.java.classpath.GlobalPathRegistry;
import org.netbeans.api.project.Project;
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

/**
 * Some NetBeans related utilities.
 *
 * @author Jonathan Lermitage
 */
public class Utils {

    private Utils() {
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
     * Get the JaCoCo report file (default is called jacoco.exec) of the given project.
     *
     * @param project the project to get JaCoCo report file.
     * @return the JaCoCo report file.
     */
    public static File getJacocoexec(Project project) {
        String jacocoExecPath = getProjectDir(project) + File.separator + "jacoco.exec";
        return new File(jacocoExecPath);
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
        if (projectClass.equals("org.netbeans.modules.java.j2seproject.J2SEProject")) {
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
     * @return a list of Java package names.
     */
    public static List<String> getProjectJavaPackages(Project project, Properties projectProperties) {
        List<String> packages = new ArrayList<String>(8);
        String srcFolderName = projectProperties.getProperty("src.dir", "src");
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
     * @param separator the separator string.
     * @param prefix a prefix to append to the end of each package name (can be empty).
     * @return a list of Java package names.
     */
    public static String getProjectJavaPackagesAsStr(Project project, Properties projectProperties, String separator, String prefix) {
        List<String> packagesList = getProjectJavaPackages(project, projectProperties);
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
     * Get the full path of the directory containing a given project.
     *
     * @param project the project.
     * @return the directory containing the project.
     */
    public static String getProjectDir(Project project) {
        return FileUtil.getFileDisplayName(project.getProjectDirectory());
    }

    public static void colorDoc(Project project, JavaClass jclass) {
        String classResource = jclass.getPackageName() + jclass.getClassName();
        FIND_JAVA_FO:
        for (FileObject curRoot : GlobalPathRegistry.getDefault().getSourceRoots()) {
            FileObject fileObject = curRoot.getFileObject(classResource);
            if (fileObject != null && fileObject.getExt().equalsIgnoreCase("JAVA")) {
                try {
                    DataObject dataObject = DataObject.find(fileObject);
                    Node node = dataObject.getNodeDelegate();
                    EditorCookie editorCookie = node.getLookup().lookup(EditorCookie.class);
                    if (editorCookie != null) {
                        StyledDocument doc = editorCookie.openDocument(); //.getDocument();
                        if (doc != null) {
                            int startLine = 0;
                            int endLine = NbDocument.findLineNumber(doc, doc.getLength());
                            Line.Set lineset = editorCookie.getLineSet();
                            for (int covIdx : jclass.getCoveredLines()) {
                                if (covIdx >= startLine && covIdx <= endLine) {
                                    Line line = lineset.getOriginal(covIdx);
                                    CoveredAnnotation annotation = new CoveredAnnotation();
                                    annotation.attach(line);
                                }
                            }
                            for (int covIdx : jclass.getPartiallyCoveredLines()) {
                                if (covIdx >= startLine && covIdx <= endLine) {
                                    Line line = lineset.getOriginal(covIdx);
                                    PartiallyCoveredAnnotation annotation = new PartiallyCoveredAnnotation();
                                    annotation.attach(line);
                                }
                            }
                            for (int covIdx : jclass.getNotCoveredLines()) {
                                if (covIdx >= startLine && covIdx <= endLine) {
                                    Line line = lineset.getOriginal(covIdx);
                                    NotCoveredAnnotation annotation = new NotCoveredAnnotation();
                                    annotation.attach(line);
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
