package fr.tikione.jacocoverage.plugin;

import fr.tikione.jacocoverage.plugin.config.Globals;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Properties;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.NAME;
import static javax.swing.Action.SMALL_ICON;
import javax.swing.JMenuItem;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.util.ContextAwareAction;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.Presenter;

/**
 * The "Run with JaCoCoverage" contextual action registration.
 * Start the "run" Ant task with the JaCoCo JavaAgent correctly configured, colorize Java source files and show a coverage report.
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
    private static final class ContextAction extends JaCoCoContextAction implements Presenter.Popup {

        private static final long serialVersionUID = 1L;

        /**
         * Enable the context action on supported projects only.
         *
         * @param context the context the action is called from.
         */
        public ContextAction(Lookup context) {
            super(context, context.lookup(Project.class), "run");
            putValue(NAME, Bundle.CTL_RunProjectWithJaCoCoAction());
            putValue(SMALL_ICON, ImageUtilities.loadImageIcon(Globals.JACOCOACTION_ICON, false));
            FileObject prjPropsFo = getProject().getProjectDirectory().getFileObject("nbproject/project.properties");
            final Properties prjProps = new Properties();
            try {
                prjProps.load(prjPropsFo.getInputStream());
                if (prjProps.getProperty("main.class", "").isEmpty()) {
                    setEnabled(false);
                }
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }

        @Override
        public JMenuItem getPopupPresenter() {
            return new JMenuItem(this);
        }
    }
}
