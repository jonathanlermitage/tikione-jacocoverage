package fr.tikione.jacocoverage.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileUtil;
import org.openide.modules.InstalledFileLocator;

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
    public static File getJacocoAgentJar() { // TODO synchronize with JaCoCo Library plugin
        return InstalledFileLocator.getDefault().locate("modules/ext/jacocoagent-0.6.2.jar", "fr.tikione.jacoco.lib", false);
    }

    /**
     * Get the JaCoCo-Ant JAR file that is registered in the IDE.
     *
     * @return the JaCoCo-Ant JAR.
     */
    public static File getJacocoAntJar() { // TODO synchronize with JaCoCo Library plugin
        return InstalledFileLocator.getDefault().locate("modules/ext/jacocoant-0.6.2.jar", "fr.tikione.jacoco.lib", false);
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
     * Retrieve the list of Java packages of a given project.
     * Each element is a fully qualified package name, e.g. <code>foo.bar</code> and <code>foo.bar.too</code>.
     *
     * @param project the project to list Java packages.
     * @return a list of Java package names.
     */
    public static List<String> getProjectJavaPackages(Project project) {
        List<String> packages = new ArrayList<String>(8);
        // TODO get project's packages
        
        return packages;
    }

    /**
     * Retrieve the list of Java packages of a given project.
     * Each element is a fully qualified package name, e.g. <code>foo.bar</code> and <code>foo.bar.too</code>. 
     * Elements are separated with a given separator string.
     *
     * @param project the project to list Java packages.
     * @param separator the separator string.
     * @return a list of Java package names.
     */
    public static String getProjectJavaPackagesAsStr(Project project, String separator) {
        List<String> packagesList = getProjectJavaPackages(project);
        StringBuilder packages = new StringBuilder(128);
        if (packagesList.isEmpty()) {
            packages.append("*");
        } else {
            boolean first = true;
            for (String pack : packagesList) {
                if (!first) {
                    packages.append(separator);
                }
                packages.append(pack);
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
}
