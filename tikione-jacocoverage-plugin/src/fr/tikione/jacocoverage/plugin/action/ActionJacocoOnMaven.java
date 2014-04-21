package fr.tikione.jacocoverage.plugin.action;

import fr.tikione.jacocoverage.plugin.util.NBUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import org.netbeans.api.project.Project;
import org.openide.util.Exceptions;
import org.openide.util.RequestProcessor;

/**
 * A toolkit that loads a Jacoco binary or XML report, colorizes Java source files and shows a coverage report for Maven projects.
 * <br/>See <a href="http://wiki.netbeans.org/DevFaqRequestProcessor">DevFaqRequestProcessor</a> for NetBeans threading tweaks.
 * <br/>See <a href="http://wiki.netbeans.org/DevFaqActionContextSensitive">DevFaqActionContextSensitive</a> for context action tweaks.
 * <br/>See <a href="http://wiki.netbeans.org/DevFaqAddGlobalContext">DevFaqAddGlobalContext</a> for global context and project tweaks.
 *
 * @author Jonathan Lermitage
 */
@SuppressWarnings("CloneableImplementsClone")
public abstract class ActionJacocoOnMaven
        extends AbstractAction
        implements ActionListener {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(ActionJacocoOnMaven.class.getName());

    /**
     * Enable the context action on supported projects only.
     */
    public ActionJacocoOnMaven() {
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        new RequestProcessor("JaCoCoverage Maven Preparation Task", 3, true).post(new Runnable() {
            @Override
            public void run() {
                try {
                    loadJacocoReport(NBUtils.getSelectedProject());
                } catch (IllegalArgumentException | IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        });
    }

    /**
     * Load a Jacoco binary report, collect and display coverage data.
     *
     * @param project the project to launch Ant target from.
     * @throws IOException if an I/O error occurs.
     */
    private void loadJacocoReport(final Project project)
            throws IOException {
        // TODO locate and load the Jacoco report file
    }
}
