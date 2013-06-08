package fr.tikione.jacocoverage.plugin.action;

import fr.tikione.jacocoverage.plugin.Utils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * The "Test with JaCoCoverage" toolbar action registration.
 * Start the "test" Ant task with the JaCoCo JavaAgent correctly configured, colorize Java source files and show a coverage report.
 *
 * @author Jonathan Lermitage
 * @author Jan Lahoda (patch https://github.com/jonathanlermitage/tikione-jacocoverage/pull/3)
 */
@ActionID(category = "Project",
          id = "fr.tikione.jacocoverage.plugin.action.ToolbarAntTestProject")
@ActionRegistration(displayName = "#CTL_ToolbarAntTestProject",
                    lazy = true,
                    asynchronous = true,
                    surviveFocusChange = true,
                    iconBase = "fr/tikione/jacocoverage/plugin/resources/icon/famamfam_script_test.png")
@ActionReference(path = "Toolbars/Build",
                 position = 450)
@NbBundle.Messages("CTL_ToolbarAntTestProject=Test Project with JaCoCoverage")
public class ToolbarAntTestProject
        extends JaCoCoActionOnAnt
        implements ContextAwareAction, ActionListener {

    private static final long serialVersionUID = 1L;

    public ToolbarAntTestProject() {
        this(Utilities.actionsGlobalContext());
    }

    public ToolbarAntTestProject(Lookup context) {
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
        return new ToolbarAntTestProject(context);
    }
}
