package fr.tikione.jacocoverage.plugin.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import org.netbeans.api.project.Project;
import org.openide.util.RequestProcessor;

/**
 * A toolkit that connects to a living JaCoCo JavaAgent, colorizes Java source files and shows a coverage report.
 * <br/>See <a href="http://wiki.netbeans.org/DevFaqRequestProcessor">DevFaqRequestProcessor</a> for NetBeans threading tweaks.
 * <br/>See <a href="http://wiki.netbeans.org/DevFaqActionContextSensitive">DevFaqActionContextSensitive</a> for context action tweaks.
 * <br/>See <a href="http://wiki.netbeans.org/DevFaqAddGlobalContext">DevFaqAddGlobalContext</a> for global context and project tweaks.
 *
 * @author Jonathan Lermitage
 */
@SuppressWarnings("CloneableImplementsClone")
public abstract class JaCoCoActionOnAntJ2EE
        extends AbstractAction
        implements ActionListener {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(JaCoCoActionOnAntJ2EE.class.getName());

    /**
     * Enable the context action on supported projects only.
     */
    public JaCoCoActionOnAntJ2EE() {
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        new RequestProcessor("JaCoCoverage Preparation Task", 3, true).post(new Runnable() {
            @Override
            public void run() {
//                try {
//                    loadJacocoReport(NBUtils.getSelectedProject());
//                } catch (IllegalArgumentException | IOException ex) {
//                    Exceptions.printStackTrace(ex);
//                }
            }
        });
    }

    /**
     * Load a JaCoCo report file, collect and display coverage data.
     *
     * @param project the project to launch Ant target from.
     * @throws IOException if an I/O error occurs.
     */
    private void loadJacocoReport(final Project project, final File binReport)
            throws IOException {

    }

    /**
     * Connect to a JaCoCo living agent, collect and display coverage data.
     *
     * @param project the project to launch Ant target from.
     * @throws IOException if an I/O error occurs.
     */
    private void connectToJacocoAgent(final Project project, final String agentAddress)
            throws IOException {

    }
}
