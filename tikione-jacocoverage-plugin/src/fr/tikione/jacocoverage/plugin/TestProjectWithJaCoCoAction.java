package fr.tikione.jacocoverage.plugin;

import fr.tikione.jacocoverage.plugin.config.Globals;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.NAME;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.JMenuItem;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.ContextAwareAction;
import org.openide.util.ImageUtilities;
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
          id = "fr.tikione.jacocoverage.plugin.TestProjectWithJaCoCoAction")
@ActionRegistration(displayName = "#CTL_TestProjectWithJaCoCoAction",
                    lazy = false)
@ActionReference(path = "Projects/Actions",
                 position = 1985,
                 separatorAfter = 1986)
@NbBundle.Messages("CTL_TestProjectWithJaCoCoAction=Test with JaCoCoverage")
public final class TestProjectWithJaCoCoAction extends AbstractAction implements ContextAwareAction {

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
    private static final class ContextAction extends JaCoCoContextAction implements Presenter.Popup {

        private static final long serialVersionUID = 1L;

        /**
         * Enable the context action on supported projects only.
         *
         * @param context the context the action is called from.
         */
        public ContextAction(Lookup context) {
            super(context, context.lookup(Project.class), "test");
            putValue(NAME, Bundle.CTL_TestProjectWithJaCoCoAction());
            putValue(SMALL_ICON, ImageUtilities.loadImageIcon(Globals.JACOCOACTION_ICON, false));
        }

        @Override
        public JMenuItem getPopupPresenter() {
            return new JMenuItem(this);
        }
    }
}
