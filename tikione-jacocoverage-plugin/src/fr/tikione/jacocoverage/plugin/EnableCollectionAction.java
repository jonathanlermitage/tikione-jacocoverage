package fr.tikione.jacocoverage.plugin;

import fr.tikione.jacocoverage.plugin.config.Globals;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.apache.tools.ant.module.api.AntProjectCookie;
import org.apache.tools.ant.module.api.AntTargetExecutor;
import org.netbeans.api.project.Project;
import org.netbeans.spi.project.support.ant.AntProjectHelper;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.awt.DynamicMenuContent;
import org.openide.execution.ExecutorTask;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.util.ContextAwareAction;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbPreferences;

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
//            String msg = "Project location: " + FileUtil.getFileDisplayName(project.getProjectDirectory());
            Preferences pref = NbPreferences.forModule(EnableCollectionAction.class);
            String antTask = pref.get(Globals.PROP_TEST_ANT_TASK, Globals.DEF_TEST_ANT_TASK);
            String antTaskParams = pref.get(Globals.PROP_TEST_ANT_TASK_PARAMS, Globals.DEF_TEST_ANT_TASK_PARAMS);
            AntProjectHelper helper = null;
            Properties targetProps = new Properties();
            try {
                FileObject scriptToExecute = project.getProjectDirectory().getFileObject("build", "xml");
                DataObject dataObj = DataObject.find(scriptToExecute);
                AntProjectCookie antCookie = dataObj.getLookup().lookup(AntProjectCookie.class);
                AntTargetExecutor.Env env = new AntTargetExecutor.Env();
                AntTargetExecutor executor = AntTargetExecutor.createTargetExecutor(env);
                env.setProperties(targetProps);
                ExecutorTask task = executor.execute(antCookie, new String[]{antTask});
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            } catch (IllegalArgumentException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }
}
