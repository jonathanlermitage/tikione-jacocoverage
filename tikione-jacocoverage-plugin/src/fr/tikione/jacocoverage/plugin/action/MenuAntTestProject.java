package fr.tikione.jacocoverage.plugin.action;

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
 * The "Test with JaCoCoverage" contextual action registration.
 * Start the "test" Ant task with the JaCoCo JavaAgent correctly configured, colorize Java source files and show a coverage report.
 *
 * @author Jonathan Lermitage
 */
@ActionID(category = "Project",
          id = "fr.tikione.jacocoverage.plugin.action.MenuAntTestProject")
@ActionRegistration(displayName = "#CTL_MenuAntTestProject",
                    lazy = false,
                    asynchronous = true,
                    surviveFocusChange = true)
@ActionReference(path = "Projects/Actions",
                 position = 1985,
                 separatorAfter = 1986)
@NbBundle.Messages("CTL_MenuAntTestProject=Test with JaCoCoverage")
public class MenuAntTestProject
        extends JaCoCoActionOnAnt
        implements ContextAwareAction, Presenter.Popup {

    private static final long serialVersionUID = 1L;

    public MenuAntTestProject() {
        super("test");
        setEnabled(Utils.isProjectSupported(NBUtils.getSelectedProject()));
        putValue(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
        putValue(Action.NAME, Bundle.CTL_MenuAntTestProject());
//        putValue(Action.SMALL_ICON, ImageUtilities.loadImageIcon(Globals.TEST_ICON, false));
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (isEnabled()) {
            super.actionPerformed(ev);
        }
    }

    @Override
    public Action createContextAwareInstance(Lookup context) {
        return new MenuAntTestProject();
    }
    
    @Override
    public JMenuItem getPopupPresenter() {
        JMenuItem menuitem = new JMenuItem(this);
        menuitem.putClientProperty(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
        return menuitem;
    }
}
