package fr.tikione.jacocoverage.plugin.action;

import fr.tikione.jacocoverage.plugin.util.NBProjectTypeEnum;
import fr.tikione.jacocoverage.plugin.util.NBUtils;
import fr.tikione.jacocoverage.plugin.util.Utils;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JMenuItem;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.DynamicMenuContent;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.Presenter;

/**
 * The "Load Jacoco binary or XML report" contextual action registration for Maven projects.
 * Load Jacoco binary or XML report, colorize Java source files and show a coverage report.
 *
 * @author Jonathan Lermitage
 */
@ActionID(category = "Project",
          id = "fr.tikione.jacocoverage.plugin.action.ProjectMavenLoadReport")
@ActionRegistration(displayName = "#CTL_ProjectMavenLoadReport",
                    lazy = false,
                    asynchronous = true,
                    surviveFocusChange = true)
@ActionReference(path = "Projects/Actions",
                 position = 1984,
                 separatorBefore = 1983)
@NbBundle.Messages("CTL_ProjectMavenLoadReport=Load Jacoco report")
@SuppressWarnings("CloneableImplementsClone")
public class ProjectMavenLoadReport
        extends ActionJacocoOnMaven
        implements ContextAwareAction, Presenter.Popup {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("NestedAssignment")
    public ProjectMavenLoadReport() {
        super();
        Project project = NBUtils.getSelectedProject();
        setEnabled(Utils.isProjectSupported(project, NBProjectTypeEnum.MAVEN));
        putValue(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
        putValue(Action.NAME, Bundle.CTL_ProjectMavenLoadReport());
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (isEnabled()) {
            super.actionPerformed(ev);
        }
    }

    @Override
    public Action createContextAwareInstance(Lookup context) {
        return new ProjectMavenLoadReport();
    }

    @Override
    public JMenuItem getPopupPresenter() {
        JMenuItem menuitem = new JMenuItem(this);
        menuitem.putClientProperty(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
        return menuitem;
    }
}
