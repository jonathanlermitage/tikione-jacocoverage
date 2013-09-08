package fr.tikione.jacocoverage.plugin.config;

import fr.tikione.jacocoverage.plugin.util.NBUtils;
import fr.tikione.jacocoverage.plugin.util.Utils;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import org.netbeans.api.project.Project;
import org.openide.awt.Mnemonics;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

/**
 * Configuration panel at project level.
 *
 * @author Jonathan Lermitage
 */
public class PrjcfgAntJavasePanel extends javax.swing.JPanel implements IStorable {

    private static final long serialVersionUID = 1L;

    /** The file to save project side configuration. */
    private File prjCfgFile;

    /** The folder that contains project's Java sources. */
    private File prjSrcDir;

    /** The list of non-empty packages found on the project associated to the current configuration panel. */
    private List<File> pkgs;

    /** The model used to handle packages filter table. */
    private DefaultTableModel pkgfltModel;

    /**
     * Creates new form PrjcfgAntJavasePanel.
     *
     * @param context
     * @throws FileNotFoundException
     * @throws IOException
     */
    public PrjcfgAntJavasePanel(Lookup context)
            throws FileNotFoundException,
                   IOException {
        super();
        initComponents();
        // <editor-fold defaultstate="collapsed" desc="Tooltips">
        // Warning: background of tooltips is black on Ubuntu. Avoid coloring links with blue.
        jButtonSocialTwitter.setToolTipText("<html><body>Jonathan Lermitage on <b>Twitter</b> (author of JaCoCoverage)<br>"
                + "https://twitter.com/JLermitage</body></html>");
        jButtonSocialFacebook.setToolTipText("<html><body>Jonathan Lermitage on <b>Facebook</b> (author of JaCoCoverage)<br>"
                + "https://www.facebook.com/jonathan.lermitage</body></html>");
        jButtonSocialGithub.setToolTipText("<html><body>Jonathan Lermitage  on <b>GitHub</b> (author of JaCoCoverage)<br>"
                + "https://github.com/jonathanlermitage</body></html>");
        jButtonSocialJojohome.setToolTipText("<html><body>Jonathan Lermitage devblog (author of JaCoCoverage)<br>"
                + "http://netbeanscolors.org</body></html>");
        jButtonOnlineHelp.setToolTipText("<html><body>Online help page of JaCoCoverage<br>"
                + "http://jacocoverage.tikione.fr/redirect/help/</body></html>");
        // </editor-fold>
        Project prj = context.lookup(Project.class);
        prjCfgFile = new File(NBUtils.getProjectDir(prj), Globals.PRJ_CFG);

        // Get project sources folder.
        String prjDir = NBUtils.getProjectDir(prj) + File.separator;
        FileObject prjPropsFo = prj.getProjectDirectory().getFileObject("nbproject/project.properties");
        Properties prjProps = new Properties();
        InputStream inputStream = prjPropsFo.getInputStream();
        try {
            prjProps.load(inputStream);
        } finally {
            inputStream.close();
        }
        prjSrcDir = new File(prjDir + Utils.getProperty(prjProps, "src.dir") + File.separator);

        load();
    }

    private void loadPkgFilter(LoadPkgFilterPolicy policy) {
        try {
            ProjectConfig prjCfg = ProjectConfig.forFile(prjCfgFile);
            List<String> excludedPkg = prjCfg.getPkgclssExclude();
            pkgs = Utils.listNonEmptyPkgs(prjSrcDir);
            pkgfltModel = new PackageFilterModel();
            jTablePackageFilter.setModel(pkgfltModel);
            jTablePackageFilter.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTablePackageFilter.getColumnModel().getColumn(0).setMinWidth(0);
            jTablePackageFilter.getColumnModel().getColumn(0).setMaxWidth(200);
            jTablePackageFilter.getColumnModel().getColumn(1).setPreferredWidth(20);
            jTablePackageFilter.getColumnModel().getColumn(1).setMinWidth(0);
            jTablePackageFilter.getColumnModel().getColumn(1).setMaxWidth(200);
            for (File pkg : pkgs) {
                String pkgName = pkg.getAbsolutePath()
                        .substring(prjSrcDir.getAbsolutePath().length() + 1)
                        .replaceAll(Matcher.quoteReplacement(File.separator), ".");
                boolean select;
                switch (policy) {
                    case SELECT_FROM_CONFIG:
                        select = !excludedPkg.contains(pkgName);
                        break;
                    case SELECT_ALL:
                        select = true;
                        break;
                    case SELECT_NONE:
                        select = false;
                        break;
                    default:
                        select = true;
                }
                pkgfltModel.addRow(new Object[]{select, Globals.ICO_NB_JAVA_PKG, pkgName});
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private void load() {
        try {
            ProjectConfig prjCfg = ProjectConfig.forFile(prjCfgFile);
            boolean overrideGlobals = prjCfg.isOverrideGlobals();
            jRadioButtonUseGlobalOptions.setSelected(!overrideGlobals);
            jRadioButtonUseProjectSpecificOptions.setSelected(overrideGlobals);
            jCheckBoxEnableHighlighting.setSelected(prjCfg.isEnblHighlighting());
            jCheckBoxEnableHighlightingExtended.setSelected(prjCfg.isEnblHighlightingExtended());
            jCheckBoxEnableConsoleReport.setSelected(prjCfg.isEnblConsoleReport());
            jCheckBoxEnableHtmlReport.setSelected(prjCfg.isEnblHtmlReport());
            jCheckBoxOpenHtmlReport.setSelected(prjCfg.isOpenHtmlReport());
            jComboBoxWorkfiles.setSelectedIndex(prjCfg.getJaCoCoWorkfilesRule());
            enableProjectsideCfgUI(overrideGlobals);
            loadPkgFilter(LoadPkgFilterPolicy.SELECT_FROM_CONFIG);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public void store() {
        try {
            ProjectConfig prjCfg = ProjectConfig.forFile(prjCfgFile);
            List<String> excludedPkg = prjCfg.getPkgclssExclude();
            excludedPkg.clear();
            Map<String, Boolean> exclusionMap = PackageFilterModel.packageFilterTableToMap((PackageFilterModel) pkgfltModel);
            for (String pkg : exclusionMap.keySet()) {
                if (!exclusionMap.get(pkg)) {
                    excludedPkg.add(pkg);
                }
            }
            prjCfg.setOverrideGlobals(jRadioButtonUseProjectSpecificOptions.isSelected());
            prjCfg.setEnblHighlighting(jCheckBoxEnableHighlighting.isSelected());
            prjCfg.setEnblHighlightingExtended(jCheckBoxEnableHighlightingExtended.isSelected());
            prjCfg.setEnblConsoleReport(jCheckBoxEnableConsoleReport.isSelected());
            prjCfg.setEnblHtmlReport(jCheckBoxEnableHtmlReport.isSelected());
            prjCfg.setOpenHtmlReport(jCheckBoxOpenHtmlReport.isSelected());
            prjCfg.setJaCoCoWorkfilesRule(jComboBoxWorkfiles.getSelectedIndex());
            prjCfg.store();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private void enableProjectsideCfgUI(boolean enable) {
        jTabbedPanePrjOpts.setVisible(enable);
        if (enable) {
            jCheckBoxEnableConsoleReport.setEnabled(enable);
            jCheckBoxEnableHighlighting.setEnabled(enable);
            jCheckBoxEnableHtmlReport.setEnabled(enable);
            jComboBoxWorkfiles.setEnabled(enable);
            jLabelWorkfiles.setEnabled(enable);
            jLabelWorkfilesTips.setEnabled(enable);
            jCheckBoxEnableHighlightingExtended.setEnabled(jCheckBoxEnableHighlighting.isSelected());
            jCheckBoxOpenHtmlReport.setEnabled(jCheckBoxEnableHtmlReport.isSelected());
        }
    }

    /** How to load the package filter table. */
    private enum LoadPkgFilterPolicy {

        SELECT_FROM_CONFIG,
        SELECT_ALL,
        SELECT_NONE

    };

    /** 
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupUseGlobalOptions = new ButtonGroup();
        jRadioButtonUseGlobalOptions = new JRadioButton();
        jRadioButtonUseProjectSpecificOptions = new JRadioButton();
        jTabbedPanePrjOpts = new JTabbedPane();
        jPanelGeneralOptions = new JPanel();
        jCheckBoxEnableHighlighting = new JCheckBox();
        jCheckBoxEnableHighlightingExtended = new JCheckBox();
        jCheckBoxEnableConsoleReport = new JCheckBox();
        jCheckBoxEnableHtmlReport = new JCheckBox();
        jCheckBoxOpenHtmlReport = new JCheckBox();
        jLabelWorkfiles = new JLabel();
        jComboBoxWorkfiles = new JComboBox();
        jLabelWorkfilesTips = new JLabel();
        jButtonSocialTwitter = new JButton();
        jButtonSocialFacebook = new JButton();
        jButtonSocialGithub = new JButton();
        jButtonSocialJojohome = new JButton();
        jButtonOnlineHelp = new JButton();
        jPanelPackageFiltering = new JPanel();
        jLabelSelectPackages = new JLabel();
        jScrollPane1 = new JScrollPane();
        jTablePackageFilter = new JTable();
        jButtonSelectAll = new JButton();
        jButtonUnselectAll = new JButton();
        jLabel1 = new JLabel();
        jButtonRefresh = new JButton();

        buttonGroupUseGlobalOptions.add(jRadioButtonUseGlobalOptions);
        Mnemonics.setLocalizedText(jRadioButtonUseGlobalOptions, NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jRadioButtonUseGlobalOptions.text")); // NOI18N
        jRadioButtonUseGlobalOptions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jRadioButtonUseGlobalOptionsActionPerformed(evt);
            }
        });

        buttonGroupUseGlobalOptions.add(jRadioButtonUseProjectSpecificOptions);
        Mnemonics.setLocalizedText(jRadioButtonUseProjectSpecificOptions, NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jRadioButtonUseProjectSpecificOptions.text")); // NOI18N
        jRadioButtonUseProjectSpecificOptions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jRadioButtonUseProjectSpecificOptionsActionPerformed(evt);
            }
        });

        Mnemonics.setLocalizedText(jCheckBoxEnableHighlighting, NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jCheckBoxEnableHighlighting.text")); // NOI18N
        jCheckBoxEnableHighlighting.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jCheckBoxEnableHighlightingActionPerformed(evt);
            }
        });

        Mnemonics.setLocalizedText(jCheckBoxEnableHighlightingExtended, NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jCheckBoxEnableHighlightingExtended.text")); // NOI18N

        Mnemonics.setLocalizedText(jCheckBoxEnableConsoleReport, NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jCheckBoxEnableConsoleReport.text")); // NOI18N

        Mnemonics.setLocalizedText(jCheckBoxEnableHtmlReport, NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jCheckBoxEnableHtmlReport.text")); // NOI18N
        jCheckBoxEnableHtmlReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jCheckBoxEnableHtmlReportActionPerformed(evt);
            }
        });

        Mnemonics.setLocalizedText(jCheckBoxOpenHtmlReport, NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jCheckBoxOpenHtmlReport.text")); // NOI18N

        Mnemonics.setLocalizedText(jLabelWorkfiles, NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jLabelWorkfiles.text")); // NOI18N

        jComboBoxWorkfiles.setModel(new DefaultComboBoxModel(new String[] { "keep original workfiles", "keep zipped workfiles", "delete workfiles" }));

        jLabelWorkfilesTips.setBackground(new Color(255, 255, 255));
        jLabelWorkfilesTips.setIcon(new ImageIcon(getClass().getResource("/fr/tikione/jacocoverage/plugin/resources/icon/famfamfam_information.png"))); // NOI18N
        Mnemonics.setLocalizedText(jLabelWorkfilesTips, NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jLabelWorkfilesTips.text")); // NOI18N
        jLabelWorkfilesTips.setVerticalAlignment(SwingConstants.TOP);
        jLabelWorkfilesTips.setVerticalTextPosition(SwingConstants.TOP);

        jButtonSocialTwitter.setIcon(new ImageIcon(getClass().getResource("/fr/tikione/jacocoverage/plugin/resources/icon/social_twitter.png"))); // NOI18N
        Mnemonics.setLocalizedText(jButtonSocialTwitter, NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jButtonSocialTwitter.text")); // NOI18N
        jButtonSocialTwitter.setBorderPainted(false);
        jButtonSocialTwitter.setContentAreaFilled(false);
        jButtonSocialTwitter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonSocialTwitter.setMaximumSize(new Dimension(48, 24));
        jButtonSocialTwitter.setMinimumSize(new Dimension(48, 24));
        jButtonSocialTwitter.setPreferredSize(new Dimension(48, 24));
        jButtonSocialTwitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonSocialTwitterActionPerformed(evt);
            }
        });

        jButtonSocialFacebook.setIcon(new ImageIcon(getClass().getResource("/fr/tikione/jacocoverage/plugin/resources/icon/social_facebook.png"))); // NOI18N
        Mnemonics.setLocalizedText(jButtonSocialFacebook, NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jButtonSocialFacebook.text")); // NOI18N
        jButtonSocialFacebook.setBorderPainted(false);
        jButtonSocialFacebook.setContentAreaFilled(false);
        jButtonSocialFacebook.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonSocialFacebook.setMaximumSize(new Dimension(48, 24));
        jButtonSocialFacebook.setMinimumSize(new Dimension(48, 24));
        jButtonSocialFacebook.setPreferredSize(new Dimension(48, 24));
        jButtonSocialFacebook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonSocialFacebookActionPerformed(evt);
            }
        });

        jButtonSocialGithub.setIcon(new ImageIcon(getClass().getResource("/fr/tikione/jacocoverage/plugin/resources/icon/social_github.png"))); // NOI18N
        Mnemonics.setLocalizedText(jButtonSocialGithub, NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jButtonSocialGithub.text")); // NOI18N
        jButtonSocialGithub.setBorderPainted(false);
        jButtonSocialGithub.setContentAreaFilled(false);
        jButtonSocialGithub.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonSocialGithub.setMaximumSize(new Dimension(48, 24));
        jButtonSocialGithub.setMinimumSize(new Dimension(48, 24));
        jButtonSocialGithub.setPreferredSize(new Dimension(48, 24));
        jButtonSocialGithub.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonSocialGithubActionPerformed(evt);
            }
        });

        jButtonSocialJojohome.setIcon(new ImageIcon(getClass().getResource("/fr/tikione/jacocoverage/plugin/resources/icon/social_jojowebsite.png"))); // NOI18N
        Mnemonics.setLocalizedText(jButtonSocialJojohome, NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jButtonSocialJojohome.text")); // NOI18N
        jButtonSocialJojohome.setBorderPainted(false);
        jButtonSocialJojohome.setContentAreaFilled(false);
        jButtonSocialJojohome.setCursor(new Cursor(Cursor.HAND_CURSOR));
        jButtonSocialJojohome.setMaximumSize(new Dimension(48, 24));
        jButtonSocialJojohome.setMinimumSize(new Dimension(48, 24));
        jButtonSocialJojohome.setPreferredSize(new Dimension(48, 24));
        jButtonSocialJojohome.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonSocialJojohomeActionPerformed(evt);
            }
        });

        jButtonOnlineHelp.setIcon(new ImageIcon(getClass().getResource("/fr/tikione/jacocoverage/plugin/resources/icon/famfamfam_help.png"))); // NOI18N
        Mnemonics.setLocalizedText(jButtonOnlineHelp, NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jButtonOnlineHelp.text")); // NOI18N
        jButtonOnlineHelp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonOnlineHelpActionPerformed(evt);
            }
        });

        GroupLayout jPanelGeneralOptionsLayout = new GroupLayout(jPanelGeneralOptions);
        jPanelGeneralOptions.setLayout(jPanelGeneralOptionsLayout);
        jPanelGeneralOptionsLayout.setHorizontalGroup(
            jPanelGeneralOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGeneralOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelGeneralOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelGeneralOptionsLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jCheckBoxOpenHtmlReport)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelGeneralOptionsLayout.createSequentialGroup()
                        .addComponent(jLabelWorkfiles)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBoxWorkfiles, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelGeneralOptionsLayout.createSequentialGroup()
                        .addGroup(jPanelGeneralOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelGeneralOptionsLayout.createSequentialGroup()
                                .addComponent(jButtonSocialTwitter, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonSocialFacebook, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonSocialGithub, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonSocialJojohome, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonOnlineHelp))
                            .addGroup(jPanelGeneralOptionsLayout.createSequentialGroup()
                                .addGroup(jPanelGeneralOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBoxEnableConsoleReport)
                                    .addComponent(jCheckBoxEnableHtmlReport)
                                    .addGroup(jPanelGeneralOptionsLayout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(jCheckBoxEnableHighlightingExtended))
                                    .addComponent(jCheckBoxEnableHighlighting))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabelWorkfilesTips, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanelGeneralOptionsLayout.setVerticalGroup(
            jPanelGeneralOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGeneralOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBoxEnableHighlighting)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxEnableHighlightingExtended)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxEnableConsoleReport)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBoxEnableHtmlReport)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxOpenHtmlReport)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelGeneralOptionsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelWorkfiles)
                    .addComponent(jComboBoxWorkfiles, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jLabelWorkfilesTips, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelGeneralOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonOnlineHelp)
                    .addGroup(jPanelGeneralOptionsLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(jButtonSocialJojohome, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanelGeneralOptionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonSocialTwitter, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonSocialFacebook, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonSocialGithub, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jTabbedPanePrjOpts.addTab(NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jPanelGeneralOptions.TabConstraints.tabTitle"), jPanelGeneralOptions); // NOI18N

        Mnemonics.setLocalizedText(jLabelSelectPackages, NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jLabelSelectPackages.text")); // NOI18N

        jTablePackageFilter.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTablePackageFilter.setGridColor(new Color(206, 206, 206));
        jTablePackageFilter.setRowSelectionAllowed(false);
        jTablePackageFilter.setShowVerticalLines(false);
        jTablePackageFilter.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTablePackageFilter);

        Mnemonics.setLocalizedText(jButtonSelectAll, NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jButtonSelectAll.text")); // NOI18N
        jButtonSelectAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonSelectAllActionPerformed(evt);
            }
        });

        Mnemonics.setLocalizedText(jButtonUnselectAll, NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jButtonUnselectAll.text")); // NOI18N
        jButtonUnselectAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonUnselectAllActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new ImageIcon(getClass().getResource("/fr/tikione/jacocoverage/plugin/resources/icon/famfamfam_information.png"))); // NOI18N
        Mnemonics.setLocalizedText(jLabel1, NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jLabel1.text")); // NOI18N
        jLabel1.setVerticalTextPosition(SwingConstants.TOP);

        jButtonRefresh.setIcon(new ImageIcon(getClass().getResource("/fr/tikione/jacocoverage/plugin/resources/icon/famfamfam_refresh.png"))); // NOI18N
        Mnemonics.setLocalizedText(jButtonRefresh, NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jButtonRefresh.text")); // NOI18N
        jButtonRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonRefreshActionPerformed(evt);
            }
        });

        GroupLayout jPanelPackageFilteringLayout = new GroupLayout(jPanelPackageFiltering);
        jPanelPackageFiltering.setLayout(jPanelPackageFilteringLayout);
        jPanelPackageFilteringLayout.setHorizontalGroup(
            jPanelPackageFilteringLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPackageFilteringLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPackageFilteringLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPackageFilteringLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelPackageFilteringLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonUnselectAll, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonSelectAll, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonRefresh, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanelPackageFilteringLayout.createSequentialGroup()
                        .addComponent(jLabelSelectPackages)
                        .addGap(0, 232, Short.MAX_VALUE))
                    .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelPackageFilteringLayout.setVerticalGroup(
            jPanelPackageFilteringLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPackageFilteringLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelSelectPackages)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPackageFilteringLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPackageFilteringLayout.createSequentialGroup()
                        .addComponent(jButtonSelectAll)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonUnselectAll)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonRefresh)
                        .addGap(0, 79, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPanePrjOpts.addTab(NbBundle.getMessage(PrjcfgAntJavasePanel.class, "PrjcfgAntJavasePanel.jPanelPackageFiltering.TabConstraints.tabTitle"), jPanelPackageFiltering); // NOI18N

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButtonUseGlobalOptions)
                    .addComponent(jRadioButtonUseProjectSpecificOptions))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jTabbedPanePrjOpts)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jRadioButtonUseGlobalOptions)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonUseProjectSpecificOptions)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPanePrjOpts, GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButtonUseGlobalOptionsActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jRadioButtonUseGlobalOptionsActionPerformed
        enableProjectsideCfgUI(false);
    }//GEN-LAST:event_jRadioButtonUseGlobalOptionsActionPerformed

    private void jRadioButtonUseProjectSpecificOptionsActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jRadioButtonUseProjectSpecificOptionsActionPerformed
        enableProjectsideCfgUI(true);
    }//GEN-LAST:event_jRadioButtonUseProjectSpecificOptionsActionPerformed

    private void jCheckBoxEnableHighlightingActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jCheckBoxEnableHighlightingActionPerformed
        jCheckBoxEnableHighlightingExtended.setEnabled(jCheckBoxEnableHighlighting.isSelected());
    }//GEN-LAST:event_jCheckBoxEnableHighlightingActionPerformed

    private void jCheckBoxEnableHtmlReportActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jCheckBoxEnableHtmlReportActionPerformed
        jCheckBoxOpenHtmlReport.setEnabled(jCheckBoxEnableHtmlReport.isSelected());
    }//GEN-LAST:event_jCheckBoxEnableHtmlReportActionPerformed

    private void jButtonSocialTwitterActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonSocialTwitterActionPerformed
        NBUtils.extBrowser("https://twitter.com/JLermitage");
    }//GEN-LAST:event_jButtonSocialTwitterActionPerformed

    private void jButtonSocialFacebookActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonSocialFacebookActionPerformed
        NBUtils.extBrowser("https://www.facebook.com/jonathan.lermitage");
    }//GEN-LAST:event_jButtonSocialFacebookActionPerformed

    private void jButtonSocialGithubActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonSocialGithubActionPerformed
        NBUtils.extBrowser("https://github.com/jonathanlermitage");
    }//GEN-LAST:event_jButtonSocialGithubActionPerformed

    private void jButtonSocialJojohomeActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonSocialJojohomeActionPerformed
        NBUtils.extBrowser("http://netbeanscolors.org");
    }//GEN-LAST:event_jButtonSocialJojohomeActionPerformed

    private void jButtonOnlineHelpActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonOnlineHelpActionPerformed
        NBUtils.extBrowser("http://jacocoverage.tikione.fr/redirect/help/");
    }//GEN-LAST:event_jButtonOnlineHelpActionPerformed

    private void jButtonRefreshActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed
        loadPkgFilter(LoadPkgFilterPolicy.SELECT_FROM_CONFIG);
    }//GEN-LAST:event_jButtonRefreshActionPerformed

    private void jButtonSelectAllActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonSelectAllActionPerformed
        loadPkgFilter(LoadPkgFilterPolicy.SELECT_ALL);
    }//GEN-LAST:event_jButtonSelectAllActionPerformed

    private void jButtonUnselectAllActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButtonUnselectAllActionPerformed
        loadPkgFilter(LoadPkgFilterPolicy.SELECT_NONE);
    }//GEN-LAST:event_jButtonUnselectAllActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ButtonGroup buttonGroupUseGlobalOptions;
    private JButton jButtonOnlineHelp;
    private JButton jButtonRefresh;
    private JButton jButtonSelectAll;
    private JButton jButtonSocialFacebook;
    private JButton jButtonSocialGithub;
    private JButton jButtonSocialJojohome;
    private JButton jButtonSocialTwitter;
    private JButton jButtonUnselectAll;
    private JCheckBox jCheckBoxEnableConsoleReport;
    private JCheckBox jCheckBoxEnableHighlighting;
    private JCheckBox jCheckBoxEnableHighlightingExtended;
    private JCheckBox jCheckBoxEnableHtmlReport;
    private JCheckBox jCheckBoxOpenHtmlReport;
    private JComboBox jComboBoxWorkfiles;
    private JLabel jLabel1;
    private JLabel jLabelSelectPackages;
    private JLabel jLabelWorkfiles;
    private JLabel jLabelWorkfilesTips;
    private JPanel jPanelGeneralOptions;
    private JPanel jPanelPackageFiltering;
    private JRadioButton jRadioButtonUseGlobalOptions;
    private JRadioButton jRadioButtonUseProjectSpecificOptions;
    private JScrollPane jScrollPane1;
    private JTabbedPane jTabbedPanePrjOpts;
    private JTable jTablePackageFilter;
    // End of variables declaration//GEN-END:variables
}
