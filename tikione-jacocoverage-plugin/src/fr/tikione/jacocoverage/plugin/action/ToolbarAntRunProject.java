package fr.tikione.jacocoverage.plugin.action;

import fr.tikione.jacocoverage.plugin.Utils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.Action;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.util.ContextAwareAction;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

/**
 * The "Run with JaCoCoverage" toolbar action registration.
 * Start the "run" Ant task with the JaCoCo JavaAgent correctly configured, colorize Java source files and show a coverage report.
 *
 * @author Jonathan Lermitage
 * @author Jan Lahoda (patch https://github.com/jonathanlermitage/tikione-jacocoverage/pull/3)
 */
@ActionID(category = "Project",
          id = "fr.tikione.jacocoverage.plugin.action.ToolbarAntRunProject")
@ActionRegistration(displayName = "#CTL_ToolbarAntRunProject",
                    lazy = true,
                    asynchronous = true,
                    surviveFocusChange = true,
                    iconBase = "fr/tikione/jacocoverage/plugin/resources/icon/famamfam_script_go.png")
@ActionReference(path = "Toolbars/Build",
                 position = 450)
@NbBundle.Messages("CTL_ToolbarAntRunProject=Run Project with JaCoCoverage")
public class ToolbarAntRunProject
        extends JaCoCoActionOnAnt
        implements ContextAwareAction, ActionListener {

    private static final long serialVersionUID = 1L;

    public ToolbarAntRunProject() {
        this(Utilities.actionsGlobalContext());
    }

    public ToolbarAntRunProject(Lookup context) {
        super(context.lookup(Project.class), "run");
        putValue(Action.NAME, Bundle.CTL_ToolbarAntRunProject());
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (Utils.isProjectSupported(getProject())) {
            FileObject prjPropsFo = getProject().getProjectDirectory().getFileObject("nbproject/project.properties");
            final Properties prjProps = new Properties();
            InputStream ins = null;
            try {
                if (prjPropsFo != null) {
                    prjProps.load(ins = prjPropsFo.getInputStream());
                    if (!prjProps.getProperty("main.class", "").isEmpty()) {
                        super.actionPerformed(ev);
                    }
                }
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            } finally {
                try {
                    if (ins != null) {
                        ins.close();
                    }
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }

    @Override
    public Action createContextAwareInstance(Lookup context) {
        return new ToolbarAntRunProject();
    }
}
