package fr.tikione.jacocoverage.plugin.config;

import java.io.File;
import org.netbeans.api.annotations.common.StaticResource;

/**
 * Global data.
 *
 * @author Jonathan Lermitage
 */
public class Globals {

    /** Icon attached to JaCoCoverage actions (test, test-single). */
    @StaticResource
    public static final String TEST_ICON = "fr/tikione/jacocoverage/plugin/resources/icon/famamfam_script_test.png";

    /** Icon attached to JaCoCoverage actions (run). */
    @StaticResource
    public static final String RUN_ICON = "fr/tikione/jacocoverage/plugin/resources/icon/famamfam_script_go.png";

    /** The name of the NetBeans console tab where JaCoCo reports are displayed. */
    public static final String TXTREPORT_TABNAME = " (jacocoverage report)";

    /** User preference: customization of the JavaAgent passed to the Ant task. */
    public static final String PROP_TEST_ANT_TASK_JAVAAGENT = "JaCoCoverage.JavaAgent.AntTaskJavaagent";

    /** User preference: enable code highlighting. */
    public static final String PROP_ENABLE_HIGHLIGHT = "JaCoCoverage.Editor.EnableCodeHighlighting";

    /** User preference: show a minimal textual JaCoCo report in a NetBeans console tab. */
    public static final String PROP_ENABLE_CONSOLE_REPORT = "JaCoCoverage.NbConsole.EnableReport";

    /** User preference: generate a complete HTML JaCoCo report. */
    public static final String PROP_ENABLE_HTML_REPORT = "JaCoCoverage.Html.EnableReport";

    /** User preference: automatically open generated complete HTML JaCoCo report. */
    public static final String PROP_AUTOOPEN_HTML_REPORT = "JaCoCoverage.Html.AutoOpenReport";

    /** Default configuration value: the JavaAgent arguments passed to the Ant task. */
    public static final String DEF_TEST_ANT_TASK_JAVAAGENT = "\"{pathOfJacocoagentJar}\"=includes={appPackages},destfile=jacoco.exec";

    /** Default configuration value: enable code highlighting. */
    public static final boolean DEF_ENABLE_HIGHLIGHT = true;

    /** Default configuration value: show a minimal textual JaCoCo report in a NetBeans console tab. */
    public static final boolean DEF_ENABLE_CONSOLE_REPORT = true;

    /** Default configuration value: generate a complete HTML JaCoCo report. */
    public static final boolean DEF_ENABLE_HTML_REPORT = true;

    /** Default configuration value: automatically open generated complete HTML JaCoCo report. */
    public static final boolean DEF_AUTOOPEN_HTML_REPORT = true;

    /** Default configuration value: the folder where JaCoCo HTML reports are generated. */
    public static final String DEF_HTML_REPORT_DIR = ".jacocoverage" + File.separator + "report.html" + File.separator;

    private Globals() {
    }
}
