package fr.tikione.jacocoverage.plugin.config;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;

/**
 * Plugin's configuration handler.
 *
 * @author Jonathan Lermitage
 */
public class Config {

    /** JaCoCoverage plugin's preference singleton. */
    private static final Preferences pref = NbPreferences.forModule(Config.class);

    private Config() {
    }

    /**
     * Ensures that future reads from this preference node and its descendants reflect any changes that were committed to the
     * persistent store (from any VM) prior to the {@code sync} invocation. As a side-effect, forces any changes in the contents
     * of this preference node and its descendants to the persistent store, as if the flush method had been invoked on this node.
     */
    public static void sync() {
        try {
            pref.sync();
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * Forces any changes in the contents of this preference node and its descendants to the persistent store. Once this method
     * returns successfully, it is safe to assume that all changes made in the subtree rooted at this node prior to the method
     * invocation have become permanent.
     */
    public static void flush() {
        try {
            pref.flush();
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /**
     * Get configuration value: show latest news in configuration panel.
     *
     * @return configuration value.
     */
    public static boolean isShowLatestNews() {
        return pref.getBoolean(Globals.PROP_SHOW_LATEST_NEWS, Globals.DEF_SHOW_LATEST_NEWS);
    }

    /**
     * Get configuration value: show a minimal textual JaCoCo report in a NetBeans console tab.
     *
     * @return configuration value.
     */
    public static boolean isEnblConsoleReport() {
        return pref.getBoolean(Globals.PROP_ENABLE_CONSOLE_REPORT, Globals.DEF_ENABLE_CONSOLE_REPORT);
    }

    /**
     * Get configuration value: enable code highlighting.
     *
     * @return configuration value.
     */
    public static boolean isEnblHighlighting() {
        return pref.getBoolean(Globals.PROP_ENABLE_HIGHLIGHT, Globals.DEF_ENABLE_HIGHLIGHT);
    }

    /**
     * Get configuration value: enable extended code highlighting.
     *
     * @return configuration value.
     */
    public static boolean isEnblHighlightingExtended() {
        return pref.getBoolean(Globals.PROP_ENABLE_HIGHLIGHTEXTENDED, Globals.DEF_ENABLE_HIGHLIGHTEXTENDED);
    }

    /**
     * Get configuration value: generate a complete HTML JaCoCo report.
     *
     * @return configuration value.
     */
    public static boolean isEnblHtmlReport() {
        return pref.getBoolean(Globals.PROP_ENABLE_HTML_REPORT, Globals.DEF_ENABLE_HTML_REPORT);
    }

    /**
     * Get configuration value: automatically open generated complete HTML JaCoCo report.
     *
     * @return configuration value.
     */
    public static boolean isOpenHtmlReport() {
        return pref.getBoolean(Globals.PROP_AUTOOPEN_HTML_REPORT, Globals.DEF_AUTOOPEN_HTML_REPORT);
    }

    /**
     * Get configuration value: the JavaAgent arguments passed to the Ant task.
     *
     * @return configuration value.
     */
    public static String getAntTaskJavaagentArg() {
        return pref.get(Globals.PROP_TEST_ANT_TASK_JAVAAGENT, Globals.DEF_TEST_ANT_TASK_JAVAAGENT);
    }

    /**
     * Get configuration value: JaCoCoverage themePrefix.
     *
     * @return configuration value.
     */
    public static int getTheme() {
        return pref.getInt(Globals.PROP_THEME, Globals.DEF_THEME);
    }

    /**
     * Get configuration value: what to do with JaCoCo workfiles.
     *
     * @return configuration value.
     */
    public static int getJaCoCoWorkfilesRule() {
        return pref.getInt(Globals.PROP_JACOCOWORKFILES_RULE, Globals.DEF_JACOCOWORKFILES_RULE);
    }

    /**
     * Set configuration value: show latest news in configuration panel.
     *
     * @param show configuration value.
     */
    public static void setShowLatestNews(boolean show) {
        pref.putBoolean(Globals.PROP_SHOW_LATEST_NEWS, show);
    }

    /**
     * Set configuration value: the JavaAgent arguments passed to the Ant task.
     *
     * @param agentArg configuration value.
     */
    public static void setAntTaskJavaagentArg(String agentArg) {
        pref.put(Globals.PROP_TEST_ANT_TASK_JAVAAGENT, agentArg);
    }

    /**
     * Set configuration value: show a minimal textual JaCoCo report in a NetBeans console tab.
     *
     * @param enbl configuration value.
     */
    public static void setEnblConsoleReport(boolean enbl) {
        pref.putBoolean(Globals.PROP_ENABLE_CONSOLE_REPORT, enbl);
    }

    /**
     * Set configuration value: enable code highlighting.
     *
     * @param enbl configuration value.
     */
    public static void setEnblHighlighting(boolean enbl) {
        pref.putBoolean(Globals.PROP_ENABLE_HIGHLIGHT, enbl);
    }

    /**
     * Set configuration value: enable extended code highlighting.
     *
     * @param enbl configuration value.
     */
    public static void setEnblHighlightingExtended(boolean enbl) {
        pref.putBoolean(Globals.PROP_ENABLE_HIGHLIGHTEXTENDED, enbl);
    }

    /**
     * Set configuration value: generate a complete HTML JaCoCo report.
     *
     * @param enbl configuration value.
     */
    public static void setEnblHtmlReport(boolean enbl) {
        pref.putBoolean(Globals.PROP_ENABLE_HTML_REPORT, enbl);
    }

    /**
     * Set configuration value: automatically open generated complete HTML JaCoCo report.
     *
     * @param enbl configuration value.
     */
    public static void setOpenHtmlReport(boolean enbl) {
        pref.putBoolean(Globals.PROP_AUTOOPEN_HTML_REPORT, enbl);
    }

    /**
     * Set configuration value: JaCoCoverage themePrefix.
     *
     * @param theme configuration value.
     */
    public static void setTheme(int theme) {
        pref.putInt(Globals.PROP_THEME, theme);
    }

    /**
     * Set configuration value: what to do with JaCoCo workfiles.
     *
     * @param rule configuration value.
     */
    public static void setJaCoCoWorkfilesRule(int rule) {
        pref.putInt(Globals.PROP_JACOCOWORKFILES_RULE, rule);
    }
}
