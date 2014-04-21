package fr.tikione.jacocoverage.plugin.action;

import fr.tikione.jacocoverage.plugin.util.NBProjectTypeEnum;
import fr.tikione.jacocoverage.plugin.util.NBUtils;
import fr.tikione.jacocoverage.plugin.util.Utils;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JMenuItem;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.DynamicMenuContent;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.Presenter;

/**
 * The "Reset coverage data" contextual action registration for J2SE and Maven projects.
 *
 * @author Jonathan Lermitage
 */
@ActionID(category = "Project",
        id = "fr.tikione.jacocoverage.plugin.action.ProjectRemoveCoverageData")
@ActionRegistration(displayName = "#CTL_ProjectRemoveCoverageData",
        lazy = false,
        asynchronous = true,
        surviveFocusChange = true)
@ActionReference(path = "Projects/Actions",
        position = 1986,
        separatorAfter = 1987)
@NbBundle.Messages("CTL_ProjectRemoveCoverageData=Reset coverage data")
@SuppressWarnings("CloneableImplementsClone")
public class ProjectRemoveCoverageData
        extends ActionRemoveCoverageData
        implements ContextAwareAction, Presenter.Popup {

    private static final long serialVersionUID = 1L;

    public ProjectRemoveCoverageData() {
        super();
        setEnabled(
                Utils.isProjectSupported(NBUtils.getSelectedProject(), true, NBProjectTypeEnum.J2SE)
                || Utils.isProjectSupported(NBUtils.getSelectedProject(), false, NBProjectTypeEnum.MAVEN__ALL));
        putValue(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
        putValue(Action.NAME, Bundle.CTL_ProjectRemoveCoverageData());
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (isEnabled()) {
            super.actionPerformed(ev);
        }
    }

    @Override
    public Action createContextAwareInstance(Lookup context) {
        return new ProjectRemoveCoverageData();
    }

    @Override
    public JMenuItem getPopupPresenter() {
        JMenuItem menuitem = new JMenuItem(this);
        menuitem.putClientProperty(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
        return menuitem;
    }
}
