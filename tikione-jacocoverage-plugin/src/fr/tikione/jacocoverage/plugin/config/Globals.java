package fr.tikione.jacocoverage.plugin.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;

/**
 * Global data.
 *
 * @author Jonathan Lermitage
 */
public class Globals {

	/** NetBeans icon representing a Java package. */
	public static final ImageIcon ICO_NB_JAVA_PKG = new ImageIcon(PackageFilterModel.class.getResource(
			"/fr/tikione/jacocoverage/plugin/resources/icon/netbeans_java_package.png"));

	/** Preview of the regular light theme. */
	public static final String THEME_ICO_REGULAR = "/fr/tikione/jacocoverage/plugin/resources/icon/theme_default.png";

	/** Preview of the "Norway Today" dark theme. */
	public static final String THEME_ICO_NORWAYTODAY = "/fr/tikione/jacocoverage/plugin/resources/icon/theme_norwaytoday.png";

	/** Color themes are associated to XML configuration files: here are their filename prefix.
	 * See the {@code fr.tikione.jacocoverage.plugin.resources} package for XML configuration files. */
	public static final List<String> THEME_PREFIX = Arrays.asList("", "norwaytoday__");

	/** The name of the NetBeans console tab where JaCoCo reports are displayed. */
	public static final String TXTREPORT_TABNAME = " (jacocoverage report)";

	/** Project preference: override globals. */
	public static final String PROP_PRJ_OVERRIDE_GLOBALS = "JaCoCoverage.Prj.OverrideGlobals";

	/** User preference: customization of the JavaAgent passed to the Ant task. */
	public static final String PROP_TEST_ANT_TASK_JAVAAGENT = "JaCoCoverage.JavaAgent.AntTaskJavaagent";

	/** User preference: enable extended code highlighting. */
	public static final String PROP_ENABLE_HIGHLIGHTEXTENDED = "JaCoCoverage.Editor.EnableCodeHighlightingExtended";

	/** User preference: enable code highlighting. */
	public static final String PROP_ENABLE_HIGHLIGHT = "JaCoCoverage.Editor.EnableCodeHighlighting";

	/** User preference: JaCoCoverage themePrefix. */
	public static final String PROP_THEME = "JaCoCoverage.Editor.Theme";

	/** User preference: what to do with JaCoCo workfiles. */
	public static final String PROP_JACOCOWORKFILES_RULE = "JaCoCoverage.JaCoCoWorkfiles.Rule";

	/** User preference: show a minimal textual JaCoCo report in a NetBeans console tab. */
	public static final String PROP_ENABLE_CONSOLE_REPORT = "JaCoCoverage.NbConsole.EnableReport";

	/** User preference: generate a complete HTML JaCoCo report. */
	public static final String PROP_ENABLE_HTML_REPORT = "JaCoCoverage.Html.EnableReport";

	/** User preference: automatically open generated complete HTML JaCoCo report. */
	public static final String PROP_AUTOOPEN_HTML_REPORT = "JaCoCoverage.Html.AutoOpenReport";

	/** Default project's configuration value: override globals. */
	public static final boolean DEF_PRJ_OVERRIDE_GLOBALS = false;

	/** Default configuration value: enable code highlighting. */
	public static final boolean DEF_ENABLE_HIGHLIGHT = true;

	/** Default configuration value: enable extended code highlighting. */
	public static final boolean DEF_ENABLE_HIGHLIGHTEXTENDED = true;

	/** Default configuration value: JaCoCoverage themePrefix. */
	public static final int DEF_THEME = 0;

	/** Default configuration value: what to do with JaCoCo workfiles. */
	public static final int DEF_JACOCOWORKFILES_RULE = 0; // 0: keep, 1: keep zipped, 2: delete

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

	/** The name of the entry stored in the zipped JaCoCo binary report. */
	public static final String BINZIP_BACKUP_REPORT_ENTRY = "jacoco.latest.exec";

	/** The name of the entry stored in the zipped JaCoCo XML report. */
	public static final String XMLZIP_BACKUP_REPORT_ENTRY = "jacoco.latest.xml";

	/** The file where zipped JaCoCo binary reports are stored. */
	public static final String BINZIP_BACKUP_REPORT = JACOCOVERAGE_DATA_DIR + "jacoco.latest.exec.zip";

	/** The file where zipped JaCoCo XML reports are stored. */
	public static final String XMLZIP_BACKUP_REPORT = JACOCOVERAGE_DATA_DIR + "jacoco.latest.xml.zip";

	/** The file where raw JaCoCo reports are stored. */
	public static final String BIN_BACKUP_REPORT = JACOCOVERAGE_DATA_DIR + "jacoco.latest.exec";

	/** The file where XML JaCoCo reports are stored. */
	public static final String XML_BACKUP_REPORT = JACOCOVERAGE_DATA_DIR + "jacoco.latest.xml";

	/** Project's JaCoCoverage properties. */
	public static final String PRJ_CFG = JACOCOVERAGE_DATA_DIR + "project.json";

	/** User preferance: use a custom JaCoCo jar instead of the bundled version.
	 * @since 1.5.2
	 */
	public static final String PROP_USE_CUSTOM_JACOCO_JAR = "JaCoCoverage.JavaAgent.useCustomJacocoJar";

	/** Default configuration value: use a custom JaCoCo jar instead of the bundled version.
	 * @since 1.5.2
	 */
	public static final boolean DEF_USE_CUSTOM_JACOCO_JAR = false;

	/** User preferance: path of custom JaCoCo jar to use instead of the bundled version.
	 * @since 1.5.2
	 */
	public static final String PROP_CUSTOM_JACOCO_JAR_PATH = "JaCoCoverage.JavaAgent.customJacocoJarPath";

	/** Default configuration value: path of custom JaCoCo jar to use instead of the bundled version.
	 * @since 1.5.2
	 */
	public static final String DEF_CUSTOM_JACOCO_JAR_PATH = "/foo/bar/jacocoagent.jar";

	private Globals() {
	}
}
