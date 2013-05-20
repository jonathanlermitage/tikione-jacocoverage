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

    private static final Preferences pref = NbPreferences.forModule(Config.class);

    private Config() {
    }

    public static void sync() {
        try {
            pref.sync();
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public static void flush() {
        try {
            pref.flush();
        } catch (BackingStoreException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public static boolean isEnblConsoleReport() {
        return pref.getBoolean(Globals.PROP_ENABLE_CONSOLE_REPORT, Globals.DEF_ENABLE_CONSOLE_REPORT);
    }

    public static boolean isEnblHighlighting() {
        return pref.getBoolean(Globals.PROP_ENABLE_HIGHLIGHT, Globals.DEF_ENABLE_HIGHLIGHT);
    }

    public static boolean isEnblHtmlReport() {
        return pref.getBoolean(Globals.PROP_ENABLE_HTML_REPORT, Globals.DEF_ENABLE_HTML_REPORT);
    }

    public static boolean isOpenHtmlReport() {
        return pref.getBoolean(Globals.PROP_AUTOOPEN_HTML_REPORT, Globals.DEF_AUTOOPEN_HTML_REPORT);
    }

    public static String getAntTaskJavaagentArg() {
        return pref.get(Globals.PROP_TEST_ANT_TASK_JAVAAGENT, Globals.DEF_TEST_ANT_TASK_JAVAAGENT);
    }

    public static void setAntTaskJavaagentArg(String agentArg) {
        pref.put(Globals.PROP_TEST_ANT_TASK_JAVAAGENT, agentArg);
    }

    public static void setEnblConsoleReport(boolean enbl) {
        pref.putBoolean(Globals.PROP_ENABLE_CONSOLE_REPORT, enbl);
    }

    public static void setEnblHighlighting(boolean enbl) {
        pref.putBoolean(Globals.PROP_ENABLE_HIGHLIGHT, enbl);
    }

    public static void setEnblHtmlReport(boolean enbl) {
        pref.putBoolean(Globals.PROP_ENABLE_HTML_REPORT, enbl);
    }

    public static void setOpenHtmlReport(boolean enbl) {
        pref.putBoolean(Globals.PROP_AUTOOPEN_HTML_REPORT, enbl);
    }
}
