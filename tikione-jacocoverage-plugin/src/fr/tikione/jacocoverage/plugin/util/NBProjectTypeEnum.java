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
    J2EE_EJB;

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
            default:
                qname = "n/a";

        }
        return qname;
    }
}
