package fr.tikione.jacocoverage.plugin.action;

import fr.tikione.jacocoverage.plugin.anno.AbstractCoverageAnnotation;
import fr.tikione.jacocoverage.plugin.util.NBUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.project.Project;
import org.openide.util.Exceptions;
import org.openide.util.RequestProcessor;

/**
 * A toolkit that launches Ant tasks with the JaCoCo JavaAgent, colorizes Java source files and shows a coverage report.
 * <br/>See <a href="http://wiki.netbeans.org/DevFaqRequestProcessor">DevFaqRequestProcessor</a> for NetBeans threading tweaks.
 * <br/>See <a href="http://wiki.netbeans.org/DevFaqActionContextSensitive">DevFaqActionContextSensitive</a> for context action tweaks.
 * <br/>See <a href="http://wiki.netbeans.org/DevFaqAddGlobalContext">DevFaqAddGlobalContext</a> for global context and project tweaks.
 *
 * @author Jonathan Lermitage
 */
@SuppressWarnings("CloneableImplementsClone")
public abstract class RemovePrjCoverageAction
        extends AbstractAction
        implements ActionListener {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(RemovePrjCoverageAction.class.getName());

    /**
     * Enable the context action on supported projects only.
     */
    public RemovePrjCoverageAction() {
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        new RequestProcessor("JaCoCoverage Cleanup Task", 3, true).post(new Runnable() {
            @Override
            public void run() {
                try {
                    removePrjCoverageData(NBUtils.getSelectedProject());
                } catch (IllegalArgumentException | IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        });
    }

    /**
     * Run an Ant task with a JaCoCo Java Agent, collect and display coverage data.
     *
     * @param project the project to launch Ant target from.
     * @throws IOException if an I/O error occurs.
     */
    private void removePrjCoverageData(final Project project)
            throws IOException {
        new RequestProcessor("JaCoCoverage Cleanup Task", 3, true).post(new Runnable() {
            @Override
            public void run() {
                ProgressHandle progr = ProgressHandleFactory.createHandle("JaCoCoverage Cleanup Task");
                if (project == null) {
                    LOGGER.warning("Cannot find project to clear coverage data");
                } else {
                    try {
                        progr.setInitialDelay(400);
                        progr.start();
                        progr.switchToIndeterminate();
                        Project prj = NBUtils.getSelectedProject();
                        AbstractCoverageAnnotation.removeAll(NBUtils.getProjectId(prj));
                    } finally {
                        progr.finish();
                    }
                }
            }
        });
    }
}
