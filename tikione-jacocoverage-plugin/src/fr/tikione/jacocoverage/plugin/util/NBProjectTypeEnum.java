package fr.tikione.jacocoverage.plugin.util;

/**
 * NetBeans project types.
 *
 * @author Jonathan Lermitage
 */
public enum NBProjectTypeEnum {

    J2SE,
    NBMODULE,
    J2EE_WEB,
    J2EE,
    J2EE_EAR,
    J2EE_EJB,
    MAVEN,
    MAVEN__ALL;

    public String qname() {
        String qname;
        switch (this) {
            case J2SE:
                qname = "org.netbeans.modules.java.j2seproject.J2SEProject";
                break;
            case NBMODULE:
                qname = "org.netbeans.modules.apisupport.project.NbModuleProject";
                break;
            case J2EE:
                qname = "org-netbeans-modules-j2ee-archiveproject";
                break;
            case J2EE_EAR:
                qname = "org-netbeans-modules-j2ee-earproject";
                break;
            case J2EE_EJB:
                qname = "org-netbeans-modules-j2ee-ejbjarproject";
                break;
            case J2EE_WEB:
                qname = "org.netbeans.modules.web.project.WebProject";
                break;
            case MAVEN:
                qname = "org.netbeans.modules.maven.NbMavenProjectImpl";
                break;
            case MAVEN__ALL:
                qname = "org.netbeans.modules.maven";
                break;
            default:
                qname = "n/a";
        }
        return qname;
    }

    /**
     * If true, check the entire project definition qname, otherwise check if the project definition
     * qname starts with the given project type.
     *
     * @return strict level.
     */
    public boolean isStrict() {
        boolean isStrict;
        switch (this) {
            case J2SE:
            case NBMODULE:
            case J2EE:
            case J2EE_EAR:
            case J2EE_EJB:
            case J2EE_WEB:
            case MAVEN:
                isStrict = true;
                break;
            case MAVEN__ALL:
                isStrict = false;
                break;
            default:
                isStrict = true;
        }
        return isStrict;
    }
}
