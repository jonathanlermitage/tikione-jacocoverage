package fr.tikione.jacocoverage.plugin;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.DynamicMenuContent;
import org.openide.filesystems.FileUtil;
import org.openide.util.ContextAwareAction;
import org.openide.util.Lookup;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

@ActionID(category = "Project",
          id = "fr.tikione.jacocoverage.plugin.EnableCollectionAction")
@ActionRegistration(displayName = "Test with JaCoCoverage",
                    lazy = false)
@ActionReference(path = "Projects/Actions",
                 position = 1984,
                 separatorBefore = 1983,
                 separatorAfter = 1985)
public final class EnableCollectionAction extends AbstractAction implements ContextAwareAction {

    private static final long serialVersionUID = 1L;

    public @Override
    void actionPerformed(ActionEvent e) {
    }

    public @Override
    Action createContextAwareInstance(Lookup context) {
        return new ContextAction(context);
    }

    private static final class ContextAction extends AbstractAction {

        private static final long serialVersionUID = 1L;

        private final Project project;

        public ContextAction(Lookup context) {
            project = context.lookup(Project.class);
            String projectClass = project.getClass().getName();
            if (projectClass.equals("org.netbeans.modules.apisupport.project.NbModuleProject")) {
                setEnabled(true);
            } else if (projectClass.equals("org.netbeans.modules.java.j2seproject.J2SEProject")) {
                setEnabled(true);
            } else {
                setEnabled(false);
            }
            putValue(DynamicMenuContent.HIDE_WHEN_DISABLED, true);
            putValue(NAME, "Test with JaCoCoverage");
        }

        public @Override
        void actionPerformed(ActionEvent e) {
            String msg = "Project location: " + FileUtil.getFileDisplayName(project.getProjectDirectory());
            InputOutput io = IOProvider.getDefault().getIO("JaCoCoverage", true);
            //io.getOut().println(msg);
            io.getOut().close();
        }
    }
}