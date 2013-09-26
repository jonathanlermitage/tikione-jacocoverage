package fr.tikione.jacocoverage.plugin.action;

import fr.tikione.jacocoverage.plugin.util.NBUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
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
public abstract class JaCoCoActionOnAntJ2EE
        extends AbstractAction
        implements ActionListener {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(JaCoCoActionOnAntJ2EE.class.getName());

    /** The Ant task to launch. */
    private final String antTask;

    /** Additional properties passed to the Ant task. */
    private final Properties addAntTargetProps = new Properties();

    /**
     * Enable the context action on supported projects only.
     *
     * @param antTask additional properties passed to the Ant task.
     */
    public JaCoCoActionOnAntJ2EE(String antTask) {
        this.antTask = antTask;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        new RequestProcessor("JaCoCoverage Preparation Task", 3, true).post(new Runnable() {
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
     * Run an Ant task with a JaCoCo Java Agent, collect and display coverage data.
     *
     * @param project the project to launch Ant target from.
     * @throws IOException if an I/O error occurs.
     */
    private void loadJacocoReport(final Project project)
            throws IOException {

    }

    @SuppressWarnings("ReturnOfCollectionOrArrayField")
    public Properties getAddAntTargetProps() {
        return addAntTargetProps;
    }
}
