package fr.tikione.jacocoverage.plugin.util;

/**
 * NetBeans project types.
 *
 * @author Jonathan Lermitage
 */
public enum NBProjectTypeEnum {

    J2SE,
    NBMODULE;

    public String qname() {
        String qname;
        switch (this) {
            case J2SE:
                qname = "org.netbeans.modules.java.j2seproject.J2SEProject";
                break;
            case NBMODULE:
                qname = "org.netbeans.modules.apisupport.project.NbModuleProject";
                break;
            default:
                qname = "n/a";

        }
        return qname;
    }
}
