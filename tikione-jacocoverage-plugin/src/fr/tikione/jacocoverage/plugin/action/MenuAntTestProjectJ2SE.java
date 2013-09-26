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
 * The "Test with JaCoCoverage" contextual action registration for J2SE projects.
 * Start the "test" Ant task with the JaCoCo JavaAgent correctly configured, colorize Java source files and show a coverage report.
 *
 * @author Jonathan Lermitage
 */
@ActionID(category = "Project",
          id = "fr.tikione.jacocoverage.plugin.action.MenuAntTestProjectJ2SE")
@ActionRegistration(displayName = "#CTL_MenuAntTestProjectJ2SE",
                    lazy = false,
                    asynchronous = true,
                    surviveFocusChange = true)
@ActionReference(path = "Projects/Actions",
                 position = 1985,
                 separatorAfter = 1986)
@NbBundle.Messages("CTL_MenuAntTestProjectJ2SE=Test with JaCoCoverage")
public class MenuAntTestProjectJ2SE
        extends JaCoCoActionOnAnt
        implements ContextAwareAction, Presenter.Popup {

    private static final long serialVersionUID = 1L;

    public MenuAntTestProjectJ2SE() {
        super("test");
        setEnabled(Utils.isProjectSupported(NBUtils.getSelectedProject(), NBProjectTypeEnum.J2SE));
        putValue(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
        putValue(Action.NAME, Bundle.CTL_MenuAntTestProjectJ2SE());
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (isEnabled()) {
            super.actionPerformed(ev);
        }
    }

    @Override
    public Action createContextAwareInstance(Lookup context) {
        return new MenuAntTestProjectJ2SE();
    }
    
    @Override
    public JMenuItem getPopupPresenter() {
        JMenuItem menuitem = new JMenuItem(this);
        menuitem.putClientProperty(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
        return menuitem;
    }
}
