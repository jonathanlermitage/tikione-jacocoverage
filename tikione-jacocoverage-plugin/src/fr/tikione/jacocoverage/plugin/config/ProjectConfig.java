package fr.tikione.jacocoverage.plugin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ProjectConfig {

    private final Properties pref = new Properties();

    private final File prjCfgFile;

    private static final Map<File, ProjectConfig> prjCfgs = Collections.synchronizedMap(new HashMap<File, ProjectConfig>(8));

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
    }

    public void load()
            throws IOException {
        pref.clear();
        if (prjCfgFile.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            pref.putAll(mapper.readValue(prjCfgFile, Properties.class));
        }
    }

    public void store()
            throws IOException {
        if (prjCfgFile.delete()) {
            if (pref != null) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(prjCfgFile, pref);
            }
        } else {
            throw new IOException("Cannot write project's jacocoverage config to: " + prjCfgFile);
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
     * Get configuration value: show latest news in configuration panel.
     *
     * @return configuration value.
     */
    public boolean isShowLatestNews() {
        boolean res;
        if (isOverrideGlobals()) {
            res = Boolean.parseBoolean(pref.getProperty(Globals.PROP_SHOW_LATEST_NEWS, Boolean.toString(Globals.DEF_SHOW_LATEST_NEWS)));
        } else {
            res = Config.isShowLatestNews();
        }
        return res;
    }

    /**
     * Get configuration value: show a minimal textual JaCoCo report in a NetBeans console tab.
     *
     * @return configuration value.
     */
    public boolean isEnblConsoleReport() {
        return Boolean.parseBoolean(pref.getProperty(
                Globals.PROP_ENABLE_CONSOLE_REPORT, Boolean.toString(Globals.DEF_ENABLE_CONSOLE_REPORT)));
    }

    /**
     * Get configuration value: enable code highlighting.
     *
     * @return configuration value.
     */
    public boolean isEnblHighlighting() {
        return Boolean.parseBoolean(pref.getProperty(
                Globals.PROP_ENABLE_HIGHLIGHT, Boolean.toString(Globals.DEF_ENABLE_HIGHLIGHT)));
    }

    /**
     * Get configuration value: enable extended code highlighting.
     *
     * @return configuration value.
     */
    public boolean isEnblHighlightingExtended() {
        return Boolean.parseBoolean(pref.getProperty(
                Globals.PROP_ENABLE_HIGHLIGHTEXTENDED, Boolean.toString(Globals.DEF_ENABLE_HIGHLIGHTEXTENDED)));
    }

    /**
     * Get configuration value: generate a complete HTML JaCoCo report.
     *
     * @return configuration value.
     */
    public boolean isEnblHtmlReport() {
        return Boolean.parseBoolean(pref.getProperty(
                Globals.PROP_ENABLE_HTML_REPORT, Boolean.toString(Globals.DEF_ENABLE_HTML_REPORT)));
    }

    /**
     * Get configuration value: automatically open generated complete HTML JaCoCo report.
     *
     * @return configuration value.
     */
    public boolean isOpenHtmlReport() {
        return Boolean.parseBoolean(pref.getProperty(
                Globals.PROP_AUTOOPEN_HTML_REPORT, Boolean.toString(Globals.DEF_AUTOOPEN_HTML_REPORT)));
    }

    /**
     * Get configuration value: the JavaAgent arguments passed to the Ant task.
     *
     * @return configuration value.
     */
    public String getAntTaskJavaagentArg() {
        return pref.getProperty(Globals.PROP_TEST_ANT_TASK_JAVAAGENT, Globals.DEF_TEST_ANT_TASK_JAVAAGENT);
    }

    /**
     * Get configuration value: JaCoCoverage themePrefix.
     *
     * @return configuration value.
     */
    public int getTheme() {
        return Integer.parseInt(pref.getProperty(
                Globals.PROP_THEME, Integer.toString(Globals.DEF_THEME)));
    }

    /**
     * Get configuration value: what to do with JaCoCo workfiles.
     *
     * @return configuration value.
     */
    public int getJaCoCoWorkfilesRule() {
        return Integer.parseInt(pref.getProperty(
                Globals.PROP_JACOCOWORKFILES_RULE, Integer.toString(Globals.DEF_JACOCOWORKFILES_RULE)));
    }

    /**
     * Set configuration value: show latest news in configuration panel.
     *
     * @param show configuration value.
     */
    public void setShowLatestNews(boolean show) {
        pref.setProperty(Globals.PROP_SHOW_LATEST_NEWS, Boolean.toString(show));
    }

    /**
     * Set configuration value: the JavaAgent arguments passed to the Ant task.
     *
     * @param agentArg configuration value.
     */
    public void setAntTaskJavaagentArg(String agentArg) {
        pref.setProperty(Globals.PROP_TEST_ANT_TASK_JAVAAGENT, agentArg);
    }

    /**
     * Set configuration value: show a minimal textual JaCoCo report in a NetBeans console tab.
     *
     * @param enbl configuration value.
     */
    public void setEnblConsoleReport(boolean enbl) {
        pref.setProperty(Globals.PROP_ENABLE_CONSOLE_REPORT, Boolean.toString(enbl));
    }

    /**
     * Set configuration value: enable code highlighting.
     *
     * @param enbl configuration value.
     */
    public void setEnblHighlighting(boolean enbl) {
        pref.setProperty(Globals.PROP_ENABLE_HIGHLIGHT, Boolean.toString(enbl));
    }

    /**
     * Set configuration value: enable extended code highlighting.
     *
     * @param enbl configuration value.
     */
    public void setEnblHighlightingExtended(boolean enbl) {
        pref.setProperty(Globals.PROP_ENABLE_HIGHLIGHTEXTENDED, Boolean.toString(enbl));
    }

    /**
     * Set configuration value: generate a complete HTML JaCoCo report.
     *
     * @param enbl configuration value.
     */
    public void setEnblHtmlReport(boolean enbl) {
        pref.setProperty(Globals.PROP_ENABLE_HTML_REPORT, Boolean.toString(enbl));
    }

    /**
     * Set configuration value: automatically open generated complete HTML JaCoCo report.
     *
     * @param enbl configuration value.
     */
    public void setOpenHtmlReport(boolean enbl) {
        pref.setProperty(Globals.PROP_AUTOOPEN_HTML_REPORT, Boolean.toString(enbl));
    }

    /**
     * Set configuration value: JaCoCoverage themePrefix.
     *
     * @param theme configuration value.
     */
    public void setTheme(int theme) {
        pref.setProperty(Globals.PROP_THEME, Integer.toString(theme));
    }

    /**
     * Set configuration value: what to do with JaCoCo workfiles.
     *
     * @param rule configuration value.
     */
    public void setJaCoCoWorkfilesRule(int rule) {
        pref.setProperty(Globals.PROP_JACOCOWORKFILES_RULE, Integer.toString(rule));
    }
}
