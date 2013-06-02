package fr.tikione.jacocoverage.plugin.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;
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

    public static final List<String> THEME_PREFIX = Arrays.asList("", "norwaytoday__");

    /** The URL of remote file describing JaCoCoverage latest news. */
    public static final String LATEST_NEWS_URL = "http://jacocoverage.tikione.fr/jacocoverage_latest_news.txt";

    /** The name of the NetBeans console tab where JaCoCo reports are displayed. */
    public static final String TXTREPORT_TABNAME = " (jacocoverage report)";

    /** User preference: customization of the JavaAgent passed to the Ant task. */
    public static final String PROP_TEST_ANT_TASK_JAVAAGENT = "JaCoCoverage.JavaAgent.AntTaskJavaagent";

    /** User preference: show latest news in configuration panel. */
    public static final String PROP_SHOW_LATEST_NEWS = "JaCoCoverage.ConfigPanel.ShowLatestNews";

    /** User preference: enable code highlighting. */
    public static final String PROP_ENABLE_HIGHLIGHT = "JaCoCoverage.Editor.EnableCodeHighlighting";

    /** User preference: JaCoCoverage themePrefix. */
    public static final String PROP_THEME = "JaCoCoverage.Editor.Theme";

    /** User preference: show a minimal textual JaCoCo report in a NetBeans console tab. */
    public static final String PROP_ENABLE_CONSOLE_REPORT = "JaCoCoverage.NbConsole.EnableReport";

    /** User preference: generate a complete HTML JaCoCo report. */
    public static final String PROP_ENABLE_HTML_REPORT = "JaCoCoverage.Html.EnableReport";

    /** User preference: automatically open generated complete HTML JaCoCo report. */
    public static final String PROP_AUTOOPEN_HTML_REPORT = "JaCoCoverage.Html.AutoOpenReport";

    /** Default configuration value: the JavaAgent arguments passed to the Ant task. */
    public static final String DEF_TEST_ANT_TASK_JAVAAGENT = "\"{pathOfJacocoagentJar}\"=includes={appPackages},destfile=jacoco.exec";

    /** Default configuration value: show latest news in configuration panel. */
    public static final boolean DEF_SHOW_LATEST_NEWS = true;

    /** Default configuration value: enable code highlighting. */
    public static final boolean DEF_ENABLE_HIGHLIGHT = true;

    /** Default configuration value: JaCoCoverage themePrefix. */
    public static final int DEF_THEME = 0;

    /** Default configuration value: show a minimal textual JaCoCo report in a NetBeans console tab. */
    public static final boolean DEF_ENABLE_CONSOLE_REPORT = true;

    /** Default configuration value: generate a complete HTML JaCoCo report. */
    public static final boolean DEF_ENABLE_HTML_REPORT = true;

    /** Default configuration value: automatically open generated complete HTML JaCoCo report. */
    public static final boolean DEF_AUTOOPEN_HTML_REPORT = true;

    /** The folder where JaCoCoverage generated files are stored. */
    public static final String JACOCOVERAGE_DATA_DIR = ".jacocoverage" + File.separator;

    /** The folder where JaCoCo HTML reports are generated. */
    public static final String HTML_REPORT_DIR = JACOCOVERAGE_DATA_DIR + "report.html" + File.separator;

    /** The file where JaCoCo XML reports are stored. */
    public static final String XML_BACKUP_REPORT_DIR = JACOCOVERAGE_DATA_DIR + "jacoco.latest.xml";

    public static final String PRJ_CFG = "project.properties";

    private Globals() {
    }
}
