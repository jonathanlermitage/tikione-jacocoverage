package fr.tikione.jacocoverage.plugin.action;

import fr.tikione.jacocoverage.plugin.Utils;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.DynamicMenuContent;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

/**
 * The "Test with JaCoCoverage" shortcut action registration.
 * Start the "test" Ant task with the JaCoCo JavaAgent correctly configured, colorize Java source files and show a coverage report.
 *
 * @author Jonathan Lermitage
 */
@ActionID(category = "Project",
          id = "fr.tikione.jacocoverage.plugin.action.ShortcutAntTestProject")
@ActionRegistration(displayName = "#CTL_ShortcutAntTestProject",
                    lazy = false,
                    asynchronous = true,
                    surviveFocusChange = true)
@ActionReference(path = "Shortcuts",
                 name = "O-F12")
@NbBundle.Messages("CTL_ShortcutAntTestProject=Test Project with JaCoCoverage")
public final class ShortcutAntTestProject
        extends JaCoCoActionOnAnt
        implements ContextAwareAction {

    private static final long serialVersionUID = 1L;

    public ShortcutAntTestProject() {
        this(Utilities.actionsGlobalContext());
    }

    public ShortcutAntTestProject(Lookup context) {
        super("test");
        putValue(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
        putValue(Action.NAME, Bundle.CTL_ShortcutAntTestProject());
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (Utils.isProjectSupported(getProject())) {
            super.actionPerformed(ev);
        }
    }

    @Override
    public Action createContextAwareInstance(Lookup context) {
        return new ShortcutAntTestProject(context);
    }
}
