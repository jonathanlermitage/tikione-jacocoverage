package fr.tikione.jacocoverage.plugin;

import java.awt.event.ActionEvent;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.NAME;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;

/**
 * The "Run with JaCoCoverage" contextual action registration.
 * Start an Ant task with the JaCoCo JavaAgent correctly configured, colorize Java source files and show a coverage report.
 *
 * @author Jonathan Lermitage
 */
@ActionID(category = "Project",
          id = "fr.tikione.jacocoverage.plugin.RunProjectWithJaCoCoAction")
@ActionRegistration(displayName = "#CTL_RunProjectWithJaCoCoAction",
                    lazy = false)
@ActionReference(path = "Projects/Actions",
                 position = 1984,
                 separatorBefore = 1983)
@NbBundle.Messages("CTL_RunProjectWithJaCoCoAction=Run with JaCoCoverage")
public final class RunProjectWithJaCoCoAction extends AbstractAction implements ContextAwareAction {

    private static final long serialVersionUID = 1L;

    public @Override
    void actionPerformed(ActionEvent e) {
    }

    public @Override
    Action createContextAwareInstance(Lookup context) {
        return new ContextAction(context);
    }

    /**
     * The "Test with JaCoCoverage" contextual action.
     */
    private static final class ContextAction extends JaCoCoContextAction {

        private static final long serialVersionUID = 1L;

        /**
         * Enable the context action on supported projects only.
         *
         * @param context the context the action is called from.
         */
        public ContextAction(Lookup context) {
            super(context, context.lookup(Project.class), "run");
            putValue(NAME, "Run with JaCoCoverage");
            Preferences pref = NbPreferences.forModule(JaCoCoContextAction.class);
            if (pref.get("main.class", "").isEmpty()) {
                //setEnabled(false);
            }
        }
    }
}
