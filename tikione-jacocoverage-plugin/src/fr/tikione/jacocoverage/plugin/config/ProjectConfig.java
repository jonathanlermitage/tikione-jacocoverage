package fr.tikione.jacocoverage.plugin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 * Plugin's configuration handler at project level.
 *
 * @author Jonathan Lermitage
 */
public class ProjectConfig {

    private static final Logger LOGGER = Logger.getLogger(ProjectConfig.class.getName());

    /** The map of all configuration properties. Element {@link #JSON_GENERAL} contains common properties (in a {@code Properties}
     object). Element {@link #JSON_PKGFILTER} contains configuration for packages and classes filter (an {@code ArrayList} of excluded
     elements). */
    private final Map<String, Object> pref = new HashMap<>(4);

    /** The file used for configuration persistence. */
    private final File prjCfgFile;

    /** JSon mapper. */
    private final ObjectMapper mapper = new ObjectMapper();

    /** A general cache for project configuration instances. */
    private static final Map<File, ProjectConfig> prjCfgs = Collections.synchronizedMap(new HashMap<File, ProjectConfig>(8));

    /** Key for map of configuration properties: commons properties. */
    private static final String JSON_GENERAL = "preferences";

    /** Key for map of configuration properties: packages and classes filter. */
    private static final String JSON_PKGFILTER = "pkgclss.excludelist";

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
        pref.put(JSON_GENERAL, new Properties());
        pref.put(JSON_PKGFILTER, new ArrayList<String>(16));
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        mapper.enable(SerializationFeature.EAGER_SERIALIZER_FETCH);
    }

    private Properties getinternalPref() {
        return (Properties) pref.get(JSON_GENERAL);
    }

    @SuppressWarnings("unchecked")
    private ArrayList<String> getPkgclssExclude() {
        return (ArrayList<String>) pref.get(JSON_PKGFILTER);
    }

    @SuppressWarnings("unchecked")
    private ArrayList<String> getPkgclssExcludeCopy(FilterEnum filter) {
        ArrayList<String> res;
        ArrayList<String> base = (ArrayList<String>) pref.get(JSON_PKGFILTER);
        switch (filter) {
            case KEEPONLY_CLASS:
                res = new ArrayList<>((int) (base.size() / 1.20) + 1);
                for (String elt : base) {
                    if (elt.toLowerCase(Locale.US).endsWith(".java")) {
                        res.add(elt);
                    }
                }
                break;
            case KEEPONLY_PKG:
                res = new ArrayList<>((int) (base.size() / 1.80) + 1);
                for (String elt : base) {
                    if (!elt.toLowerCase(Locale.US).endsWith(".java")) {
                        res.add(elt);
                    }
                }
                break;
            case KEEP_ALL:
            default:
                res = new ArrayList<>(base);
                break;
        }
        return res;
    }

    /**
     * Load project's configuration.
     *
     * @throws IOException if cannot load configuration.
     */
    @SuppressWarnings("unchecked")
    public void load()
            throws IOException {
        getinternalPref().clear();
        getPkgclssExclude().clear();
        prjCfgFile.getParentFile().mkdirs();
        if (prjCfgFile.exists()) {
            try {
                getinternalPref().putAll((Map<Object, Object>) mapper.readValue(prjCfgFile, Map.class).get(JSON_GENERAL));
            } catch (Exception ex) {
                LOGGER.log(Level.INFO, "Project's JaCoCoverage configuration file format is outdated or invalid. Reset cause:", ex);
                String msg = "The project's JaCoCoverage configuration file format is outdated or invalid.\n"
                        + "The configuration file has been reset to support new format.";
                DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(msg, NotifyDescriptor.WARNING_MESSAGE));
            }
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
                getinternalPref().getProperty(
                Globals.PROP_PRJ_OVERRIDE_GLOBALS, Boolean.toString(Globals.DEF_PRJ_OVERRIDE_GLOBALS)));
    }

    /**
     * Set project's configuration value: Indicate if project overrides plugin's globals options.
     *
     * @param enbl configuration value.
     */
    public void setOverrideGlobals(boolean enbl) {
        getinternalPref().setProperty(Globals.PROP_PRJ_OVERRIDE_GLOBALS, Boolean.toString(enbl));
    }

    /**
     * Get configuration value: show a minimal textual JaCoCo report in a NetBeans console tab.
     *
     * @return configuration value.
     */
    public boolean isEnblConsoleReport() {
        boolean res;
        if (isOverrideGlobals()) {
            res = Boolean.parseBoolean(getinternalPref().getProperty(
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
            res = Boolean.parseBoolean(getinternalPref().getProperty(
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
            res = Boolean.parseBoolean(getinternalPref().getProperty(
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
            res = Boolean.parseBoolean(getinternalPref().getProperty(
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
            res = Boolean.parseBoolean(getinternalPref().getProperty(
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
            res = getinternalPref().getProperty(Globals.PROP_TEST_ANT_TASK_JAVAAGENT, Globals.DEF_TEST_ANT_TASK_JAVAAGENT);
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
            res = Integer.parseInt(getinternalPref().getProperty(
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
            res = Integer.parseInt(getinternalPref().getProperty(
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
            getinternalPref().setProperty(Globals.PROP_TEST_ANT_TASK_JAVAAGENT, agentArg);
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
            getinternalPref().setProperty(Globals.PROP_ENABLE_CONSOLE_REPORT, Boolean.toString(enbl));
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
            getinternalPref().setProperty(Globals.PROP_ENABLE_HIGHLIGHT, Boolean.toString(enbl));
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
            getinternalPref().setProperty(Globals.PROP_ENABLE_HIGHLIGHTEXTENDED, Boolean.toString(enbl));
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
            getinternalPref().setProperty(Globals.PROP_ENABLE_HTML_REPORT, Boolean.toString(enbl));
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
            getinternalPref().setProperty(Globals.PROP_AUTOOPEN_HTML_REPORT, Boolean.toString(enbl));
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
            getinternalPref().setProperty(Globals.PROP_THEME, Integer.toString(theme));
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
            getinternalPref().setProperty(Globals.PROP_JACOCOWORKFILES_RULE, Integer.toString(rule));
        } else {
            Config.setJaCoCoWorkfilesRule(rule);
        }
    }
}
