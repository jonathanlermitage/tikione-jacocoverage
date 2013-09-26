package fr.tikione.jacocoverage.plugin.action;

import fr.tikione.jacocoverage.plugin.util.NBProjectTypeEnum;
import fr.tikione.jacocoverage.plugin.util.NBUtils;
import fr.tikione.jacocoverage.plugin.util.Utils;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.Action;
import javax.swing.JMenuItem;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.DynamicMenuContent;
import org.openide.filesystems.FileObject;
import org.openide.util.ContextAwareAction;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.Presenter;

/**
 * The "Run with JaCoCoverage" contextual action registration for J2SE projects.
 * Start the "run" Ant task with the JaCoCo JavaAgent correctly configured, colorize Java source files and show a coverage report.
 *
 * @author Jonathan Lermitage
 * @author Jan Lahoda (patch https://github.com/jonathanlermitage/tikione-jacocoverage/pull/3)
 */
@ActionID(category = "Project",
          id = "fr.tikione.jacocoverage.plugin.action.MenuAntRunProjectJ2SE")
@ActionRegistration(displayName = "#CTL_MenuAntRunProjectJ2SE",
                    lazy = false,
                    asynchronous = true,
                    surviveFocusChange = true)
@ActionReference(path = "Projects/Actions",
                 position = 1984,
                 separatorBefore = 1983)
@NbBundle.Messages("CTL_MenuAntRunProjectJ2SE=Run with JaCoCoverage")
public class MenuAntRunProjectJ2SE
        extends JaCoCoActionOnAnt
        implements ContextAwareAction, Presenter.Popup {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("NestedAssignment")
    public MenuAntRunProjectJ2SE() {
        super("run");
        Project project = NBUtils.getSelectedProject();
        setEnabled(Utils.isProjectSupported(project, NBProjectTypeEnum.J2SE));
        putValue(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
        putValue(Action.NAME, Bundle.CTL_MenuAntRunProjectJ2SE());
        if (isEnabled()) { // Don't try to enable if project's type is not supported.
            FileObject prjPropsFo = project.getProjectDirectory().getFileObject("nbproject/project.properties");
            final Properties prjProps = new Properties();
            InputStream ins = null;
            try {
                if (prjPropsFo != null) {
                    prjProps.load(ins = prjPropsFo.getInputStream());
                }
                if (prjProps.getProperty("main.class", "").isEmpty()) {
                    setEnabled(false);
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
    public void actionPerformed(ActionEvent ev) {
        if (isEnabled()) {
            super.actionPerformed(ev);
        }
    }

    @Override
    public Action createContextAwareInstance(Lookup context) {
        return new MenuAntRunProjectJ2SE();
    }

    @Override
    public JMenuItem getPopupPresenter() {
        JMenuItem menuitem = new JMenuItem(this);
        menuitem.putClientProperty(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
        return menuitem;
    }
}
