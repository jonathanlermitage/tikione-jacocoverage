package fr.tikione.jacocoverage.plugin.action;

import fr.tikione.jacocoverage.plugin.NBUtils;
import fr.tikione.jacocoverage.plugin.Utils;
import java.awt.event.ActionEvent;
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

/**
 * The "Run with JaCoCoverage" shortcut action registration.
 * Start the "run" Ant task with the JaCoCo JavaAgent correctly configured, colorize Java source files and show a coverage report.
 *
 * @author Jonathan Lermitage
 * @author Jan Lahoda (patch https://github.com/jonathanlermitage/tikione-jacocoverage/pull/3)
 */
@ActionID(category = "Project",
          id = "fr.tikione.jacocoverage.plugin.action.ShortcutAntRunProject")
@ActionRegistration(displayName = "#CTL_ShortcutAntRunProject",
                    lazy = false,
                    asynchronous = true,
                    surviveFocusChange = true)
@ActionReference(path = "Shortcuts",
                 name = "F12")
@NbBundle.Messages("CTL_ShortcutAntRunProject=Run Project with JaCoCoverage")
public class ShortcutAntRunProject
        extends JaCoCoActionOnAnt
        implements ContextAwareAction {

    private static final long serialVersionUID = 1L;

    public ShortcutAntRunProject() {
        super("run");
        putValue(Action.NAME, Bundle.CTL_ShortcutAntRunProject());
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        Project project = NBUtils.getSelectedProject();
        if (Utils.isProjectSupported(project)) {
            FileObject prjPropsFo = project.getProjectDirectory().getFileObject("nbproject/project.properties");
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
        return new ShortcutAntRunProject();
    }
}
