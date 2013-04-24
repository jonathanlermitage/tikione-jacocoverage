package fr.tikione.jacocoverage.plugin.config;

public class Globals {

    public static final long serialVersionUID = 1L;

    public static String PROP_COVERAGE_HILIGHT_COLOR_R = "JaCoCoverage.CoveredR";

    public static String PROP_COVERAGE_HILIGHT_COLOR_G = "JaCoCoverage.CoveredG";

    public static String PROP_COVERAGE_HILIGHT_COLOR_B = "JaCoCoverage.CoveredB";

    public static String PROP_PARTIALCOVERAGE_HILIGHT_COLOR_R = "JaCoCoverage.PartiallyCoveredR";

    public static String PROP_PARTIALCOVERAGE_HILIGHT_COLOR_G = "JaCoCoverage.PartiallyCoveredG";

    public static String PROP_PARTIALCOVERAGE_HILIGHT_COLOR_B = "JaCoCoverage.PartiallyCoveredB";

    public static String PROP_NOCOVERAGE_HILIGHT_COLOR_R = "JaCoCoverage.NotCoveredR";

    public static String PROP_NOCOVERAGE_HILIGHT_COLOR_G = "JaCoCoverage.NotCoveredG";

    public static String PROP_NOCOVERAGE_HILIGHT_COLOR_B = "JaCoCoverage.NotCoveredB";

    public static String PROP_TEST_ANT_TASK_JAVAAGENT = "JaCoCoverage.JavaAgent.AntTaskJavaagent";

    public static String PROP_ENABLE_HIGHLIGHT = "JaCoCoverage.Editor.EnableCodeHighlighting";

    public static String PROP_ENABLE_CONSOLE_REPORT = "JaCoCoverage.NbConsole.EnableReport";

    public static final int DEF_COVERED_R = 205;

    public static final int DEF_COVERED_G = 235;

    public static final int DEF_COVERED_B = 175;

    public static final int DEF_PARTIAL_COVERED_R = 255;

    public static final int DEF_PARTIAL_COVERED_G = 231;

    public static final int DEF_PARTIAL_COVERED_B = 157;

    public static final int DEF_NOT_COVERED_R = 252;

    public static final int DEF_NOT_COVERED_G = 201;

    public static final int DEF_NOT_COVERED_B = 194;

    public static String DEF_TEST_ANT_TASK_JAVAAGENT = "\"{pathOfJacocoagentJar}\"=includes={appPackages},destfile=jacoco.exec";

    public static boolean DEF_ENABLE_HIGHLIGHT = true;

    public static boolean DEF_ENABLE_CONSOLE_REPORT = true;

    public Globals() {
    }
}
