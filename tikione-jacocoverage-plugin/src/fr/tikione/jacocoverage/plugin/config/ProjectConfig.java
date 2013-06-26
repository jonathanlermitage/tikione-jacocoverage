package fr.tikione.jacocoverage.plugin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Plugin's configuration handler at project's level.
 *
 * @author Jonathan Lermitage
 */
public class ProjectConfig {

    private final Properties pref = new Properties();

    private final File prjCfgFile;

    private final ObjectMapper mapper = new ObjectMapper();

    private static final Map<File, ProjectConfig> prjCfgs = Collections.synchronizedMap(new HashMap<File, ProjectConfig>(8));

    /**
     * Get project's configuration handler.
     *
     * @param prjCfgFile the project to get configuration handler from.
     * @return project's configuration handler.
     * @throws IOException if cannot load configuration.
     */
    public static ProjectConfig forFile(File prjCfgFile)
            throws IOException {
        ProjectConfig pc;
        if (prjCfgs.containsKey(prjCfgFile)) {
            pc = prjCfgs.get(prjCfgFile);
        } else {
            pc = new ProjectConfig(prjCfgFile);
            pc.load();
            prjCfgs.put(prjCfgFile, pc);
        }
        return pc;
    }

    private ProjectConfig(File prjCfgFile) {
        this.prjCfgFile = prjCfgFile;
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        mapper.enable(SerializationFeature.EAGER_SERIALIZER_FETCH);
    }

    /**
     * Load project's configuration.
     *
     * @throws IOException if cannot load configuration.
     */
    public void load()
            throws IOException {
        pref.clear();
        prjCfgFile.getParentFile().mkdirs();
        if (prjCfgFile.exists()) {
            pref.putAll(mapper.readValue(prjCfgFile, Properties.class));
        }
    }

    /**
     * Store project's configuration.
     *
     * @throws IOException if cannot store configuration.
     */
    public void store()
            throws IOException {
        prjCfgFile.getParentFile().mkdirs();
        if (prjCfgFile.exists() && !prjCfgFile.delete()) {
            throw new IOException("Cannot write project's jacocoverage config to: " + prjCfgFile);
        } else {
            mapper.writeValue(prjCfgFile, pref);
        }
    }

    /**
     * Get project's configuration value: Indicate if project overrides plugin's globals options.
     *
     * @return configuration value.
     */
    public boolean isOverrideGlobals() {
        return Boolean.parseBoolean(
                pref.getProperty(
                Globals.PROP_PRJ_OVERRIDE_GLOBALS, Boolean.toString(Globals.DEF_PRJ_OVERRIDE_GLOBALS)));
    }

    /**
     * Set project's configuration value: Indicate if project overrides plugin's globals options.
     *
     * @param enbl configuration value.
     */
    public void setOverrideGlobals(boolean enbl) {
        pref.setProperty(Globals.PROP_PRJ_OVERRIDE_GLOBALS, Boolean.toString(enbl));
    }

    /**
     * Get configuration value: show a minimal textual JaCoCo report in a NetBeans console tab.
     *
     * @return configuration value.
     */
    public boolean isEnblConsoleReport() {
        boolean res;
        if (isOverrideGlobals()) {
            res = Boolean.parseBoolean(pref.getProperty(
                    Globals.PROP_ENABLE_CONSOLE_REPORT, Boolean.toString(Globals.DEF_ENABLE_CONSOLE_REPORT)));
        } else {
            res = Config.isEnblConsoleReport();
        }
        return res;
    }

    /**
     * Get configuration value: enable code highlighting.
     *
     * @return configuration value.
     */
    public boolean isEnblHighlighting() {
        boolean res;
        if (isOverrideGlobals()) {
            res = Boolean.parseBoolean(pref.getProperty(
                    Globals.PROP_ENABLE_HIGHLIGHT, Boolean.toString(Globals.DEF_ENABLE_HIGHLIGHT)));
        } else {
            res = Config.isEnblHighlighting();
        }
        return res;
    }

    /**
     * Get configuration value: enable extended code highlighting.
     *
     * @return configuration value.
     */
    public boolean isEnblHighlightingExtended() {
        boolean res;
        if (isOverrideGlobals()) {
            res = Boolean.parseBoolean(pref.getProperty(
                    Globals.PROP_ENABLE_HIGHLIGHTEXTENDED, Boolean.toString(Globals.DEF_ENABLE_HIGHLIGHTEXTENDED)));
        } else {
            res = Config.isEnblHighlightingExtended();
        }
        return res;
    }

    /**
     * Get configuration value: generate a complete HTML JaCoCo report.
     *
     * @return configuration value.
     */
    public boolean isEnblHtmlReport() {
        boolean res;
        if (isOverrideGlobals()) {
            res = Boolean.parseBoolean(pref.getProperty(
                    Globals.PROP_ENABLE_HTML_REPORT, Boolean.toString(Globals.DEF_ENABLE_HTML_REPORT)));
        } else {
            res = Config.isEnblHtmlReport();
        }
        return res;
    }

    /**
     * Get configuration value: automatically open generated complete HTML JaCoCo report.
     *
     * @return configuration value.
     */
    public boolean isOpenHtmlReport() {
        boolean res;
        if (isOverrideGlobals()) {
            res = Boolean.parseBoolean(pref.getProperty(
                    Globals.PROP_AUTOOPEN_HTML_REPORT, Boolean.toString(Globals.DEF_AUTOOPEN_HTML_REPORT)));
        } else {
            res = Config.isOpenHtmlReport();
        }
        return res;
    }

    /**
     * Get configuration value: the JavaAgent arguments passed to the Ant task.
     *
     * @return configuration value.
     */
    public String getAntTaskJavaagentArg() {
        String res;
        if (isOverrideGlobals()) {
            res = pref.getProperty(Globals.PROP_TEST_ANT_TASK_JAVAAGENT, Globals.DEF_TEST_ANT_TASK_JAVAAGENT);
        } else {
            res = Config.getAntTaskJavaagentArg();
        }
        return res;
    }

    /**
     * Get configuration value: JaCoCoverage themePrefix.
     *
     * @return configuration value.
     */
    public int getTheme() {
        int res;
        if (isOverrideGlobals()) {
            res = Integer.parseInt(pref.getProperty(
                    Globals.PROP_THEME, Integer.toString(Globals.DEF_THEME)));
        } else {
            res = Config.getTheme();
        }
        return res;
    }

    /**
     * Get configuration value: what to do with JaCoCo workfiles.
     *
     * @return configuration value.
     */
    public int getJaCoCoWorkfilesRule() {
        int res;
        if (isOverrideGlobals()) {
            res = Integer.parseInt(pref.getProperty(
                    Globals.PROP_JACOCOWORKFILES_RULE, Integer.toString(Globals.DEF_JACOCOWORKFILES_RULE)));
        } else {
            res = Config.getJaCoCoWorkfilesRule();
        }
        return res;
    }

    /**
     * Set configuration value: the JavaAgent arguments passed to the Ant task.
     *
     * @param agentArg configuration value.
     */
    public void setAntTaskJavaagentArg(String agentArg) {
        if (isOverrideGlobals()) {
            pref.setProperty(Globals.PROP_TEST_ANT_TASK_JAVAAGENT, agentArg);
        } else {
            Config.setAntTaskJavaagentArg(agentArg);
        }
    }

    /**
     * Set configuration value: show a minimal textual JaCoCo report in a NetBeans console tab.
     *
     * @param enbl configuration value.
     */
    public void setEnblConsoleReport(boolean enbl) {
        if (isOverrideGlobals()) {
            pref.setProperty(Globals.PROP_ENABLE_CONSOLE_REPORT, Boolean.toString(enbl));
        } else {
            Config.setEnblConsoleReport(enbl);
        }
    }

    /**
     * Set configuration value: enable code highlighting.
     *
     * @param enbl configuration value.
     */
    public void setEnblHighlighting(boolean enbl) {
        if (isOverrideGlobals()) {
            pref.setProperty(Globals.PROP_ENABLE_HIGHLIGHT, Boolean.toString(enbl));
        } else {
            Config.setEnblHighlighting(enbl);
        }
    }

    /**
     * Set configuration value: enable extended code highlighting.
     *
     * @param enbl configuration value.
     */
    public void setEnblHighlightingExtended(boolean enbl) {
        if (isOverrideGlobals()) {
            pref.setProperty(Globals.PROP_ENABLE_HIGHLIGHTEXTENDED, Boolean.toString(enbl));
        } else {
            Config.setEnblHighlightingExtended(enbl);
        }
    }

    /**
     * Set configuration value: generate a complete HTML JaCoCo report.
     *
     * @param enbl configuration value.
     */
    public void setEnblHtmlReport(boolean enbl) {
        if (isOverrideGlobals()) {
            pref.setProperty(Globals.PROP_ENABLE_HTML_REPORT, Boolean.toString(enbl));
        } else {
            Config.setEnblHtmlReport(enbl);
        }
    }

    /**
     * Set configuration value: automatically open generated complete HTML JaCoCo report.
     *
     * @param enbl configuration value.
     */
    public void setOpenHtmlReport(boolean enbl) {
        if (isOverrideGlobals()) {
            pref.setProperty(Globals.PROP_AUTOOPEN_HTML_REPORT, Boolean.toString(enbl));
        } else {
            Config.setOpenHtmlReport(enbl);
        }
    }

    /**
     * Set configuration value: JaCoCoverage themePrefix.
     *
     * @param theme configuration value.
     */
    public void setTheme(int theme) {
        if (isOverrideGlobals()) {
            pref.setProperty(Globals.PROP_THEME, Integer.toString(theme));
        } else {
            Config.setTheme(theme);
        }
    }

    /**
     * Set configuration value: what to do with JaCoCo workfiles.
     *
     * @param rule configuration value.
     */
    public void setJaCoCoWorkfilesRule(int rule) {
        if (isOverrideGlobals()) {
            pref.setProperty(Globals.PROP_JACOCOWORKFILES_RULE, Integer.toString(rule));
        } else {
            Config.setJaCoCoWorkfilesRule(rule);
        }
    }
}
