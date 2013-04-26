package fr.tikione.jacocoverage.plugin.config;

import org.netbeans.api.annotations.common.StaticResource;

/**
 * Global data.
 *
 * @author Jonathan Lermitage
 */
public class Globals {

    /** Icon attached to JaCoCoverage actions (run, test, test-single). */
    @StaticResource
    public static final String TEST_ICON = "fr/tikione/jacocoverage/plugin/resources/icon/famamfam_script_test.png";

    /** Icon attached to JaCoCoverage actions (run, test, test-single). */
    @StaticResource
    public static final String RUN_ICON = "fr/tikione/jacocoverage/plugin/resources/icon/famamfam_script_go.png";

    /** The name of the NetBeans console tab where JaCoCo reports are displayed. */
    public static final String TXTREPORT_TABNAME = "JaCoCoverage Report";

    /** User preference: highlighting color of covered code (Red part of RGB). */
    public static final String PROP_COVERAGE_HILIGHT_COLOR_R = "JaCoCoverage.CoveredR";

    /** User preference: highlighting color of covered code (Green part of RGB). */
    public static final String PROP_COVERAGE_HILIGHT_COLOR_G = "JaCoCoverage.CoveredG";

    /** User preference: highlighting color of covered code (Blue part of RGB). */
    public static final String PROP_COVERAGE_HILIGHT_COLOR_B = "JaCoCoverage.CoveredB";

    /** User preference: highlighting color of partially covered code (Red part of RGB). */
    public static final String PROP_PARTIALCOVERAGE_HILIGHT_COLOR_R = "JaCoCoverage.PartiallyCoveredR";

    /** User preference: highlighting color of partially covered code (Green part of RGB). */
    public static final String PROP_PARTIALCOVERAGE_HILIGHT_COLOR_G = "JaCoCoverage.PartiallyCoveredG";

    /** User preference: highlighting color of partially covered code (Blue part of RGB). */
    public static final String PROP_PARTIALCOVERAGE_HILIGHT_COLOR_B = "JaCoCoverage.PartiallyCoveredB";

    /** User preference: highlighting color of not covered code (Red part of RGB). */
    public static final String PROP_NOCOVERAGE_HILIGHT_COLOR_R = "JaCoCoverage.NotCoveredR";

    /** User preference: highlighting color of not covered code (Green part of RGB). */
    public static final String PROP_NOCOVERAGE_HILIGHT_COLOR_G = "JaCoCoverage.NotCoveredG";

    /** User preference: highlighting color of not covered code (Blue part of RGB). */
    public static final String PROP_NOCOVERAGE_HILIGHT_COLOR_B = "JaCoCoverage.NotCoveredB";

    /** User preference: customization of the JavaAgent passed to the Ant task. */
    public static final String PROP_TEST_ANT_TASK_JAVAAGENT = "JaCoCoverage.JavaAgent.AntTaskJavaagent";

    /** User preference: enable code highlighting. */
    public static final String PROP_ENABLE_HIGHLIGHT = "JaCoCoverage.Editor.EnableCodeHighlighting";

    /** User preference: show a minimal textual JaCoCo report in a NetBeans console tab. */
    public static final String PROP_ENABLE_CONSOLE_REPORT = "JaCoCoverage.NbConsole.EnableReport";

    /** Default highlighting color (RGB) of covered code. */
    public static final int[] DEF_COVERED_RGB = new int []{205, 235, 175};

    /** Default highlighting color (RGB) of partially covered code. */
    public static final int[] DEF_PARTIAL_COVERED_RGB = new int []{255, 231, 157};

    /** Default highlighting color (RGB) of not covered code. */
    public static final int[] DEF_NOT_COVERED_RGB = new int []{252, 201, 194};

    /** Default configuration value: the JavaAgent passed to the Ant task. */
    public static final String DEF_TEST_ANT_TASK_JAVAAGENT = "\"{pathOfJacocoagentJar}\"=includes={appPackages},destfile=jacoco.exec";

    /** Default configuration value: enable code highlighting. */
    public static final boolean DEF_ENABLE_HIGHLIGHT = true;

    /** Default configuration value: show a minimal textual JaCoCo report in a NetBeans console tab. */
    public static final boolean DEF_ENABLE_CONSOLE_REPORT = true;

    private Globals() {
    }
}
