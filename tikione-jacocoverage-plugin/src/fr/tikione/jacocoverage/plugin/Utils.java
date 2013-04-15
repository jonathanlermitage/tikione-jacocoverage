package fr.tikione.jacocoverage.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.project.Project;
import org.openide.modules.InstalledFileLocator;

public class Utils {

    private Utils() {
    }

    public static File getJacocoAgentJar() { // PENDING synchronize with JaCoCo Library plugin
        return InstalledFileLocator.getDefault().locate("modules/ext/jacocoagent-0.6.2.jar", "fr.tikione.jacoco.lib", false);
    }

    public static File getJacocoAntJar() { // PENDING synchronize with JaCoCo Library plugin
        return InstalledFileLocator.getDefault().locate("modules/ext/jacocoant-0.6.2.jar", "fr.tikione.jacoco.lib", false);
    }

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

    public static List<String> getProjectJavaPackages() {
        List<String> packages = new ArrayList<String>(8);
        // TODO get project's packages
        return packages;
    }
}
