package fr.tikione.jacocoverage.plugin.action;

import fr.tikione.jacocoverage.plugin.Utils;
import fr.tikione.jacocoverage.plugin.config.Globals;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JMenuItem;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.awt.DynamicMenuContent;
import org.openide.util.ContextAwareAction;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
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
@ActionReferences({
    @ActionReference(path = "Projects/Actions",
                     position = 1985,
                     separatorAfter = 1986),
    @ActionReference(path = "Shortcuts",
                     name = "O-F12") // Shift+F12
})
@NbBundle.Messages("CTL_MenuAntTestProject=Test with JaCoCoverage")
public final class MenuAntTestProject
        extends JaCoCoActionOnAnt
        implements ContextAwareAction, LookupListener, Presenter.Popup {

    private static final long serialVersionUID = 1L;

    public MenuAntTestProject() {
        this(Utilities.actionsGlobalContext());
    }

    public MenuAntTestProject(Lookup context) {
        super(context, context.lookup(Project.class), "test");
        setEnabled(Utils.isProjectSupported(getProject()));
        putValue(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
        putValue(Action.NAME, Bundle.CTL_MenuAntTestProject());
        putValue(Action.SMALL_ICON, ImageUtilities.loadImageIcon(Globals.TEST_ICON, false));
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (isEnabled()) {
            super.actionPerformed(ev);
        }
    }

    @Override
    public Action createContextAwareInstance(Lookup context) {
        return new MenuAntTestProject(context);
    }

    @Override
    public void resultChanged(LookupEvent ev) {
    }

    @Override
    public JMenuItem getPopupPresenter() {
        return new JMenuItem(this);
    }
}
