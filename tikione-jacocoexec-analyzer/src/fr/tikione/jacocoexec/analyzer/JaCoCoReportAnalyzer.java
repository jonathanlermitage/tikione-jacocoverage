package fr.tikione.jacocoexec.analyzer;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.data.ExecutionDataReader;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.jacoco.report.DirectorySourceFileLocator;
import org.jacoco.report.FileMultiReportOutput;
import org.jacoco.report.IReportVisitor;
import org.jacoco.report.html.HTMLFormatter;
import org.jacoco.report.xml.XMLFormatter;
import org.openide.windows.IOColorPrint;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

/**
 * JaCoCo reports related utilities.
 * <br/>See <a href="http://www.eclemma.org/jacoco/trunk/doc/index.html">JaCoCo online documentation</a>.
 *
 * @author Jonathan Lermitage
 */
public class JaCoCoReportAnalyzer {

    /** Encoding used by JaCoCo. */
    private static final String DEF_ENCODING = "UTF-8";

    /** NetBeans console: color associated to covered instructions. */
    private static final Color CONSOLE_COVERED = new Color(44, 126, 0);

    /** NetBeans console: color associated to partially covered instructions. */
    private static final Color CONSOLE_PARTIALLY_COVERED = new Color(186, 93, 0);

    /** NetBeans console: color associated to not covered instructions. */
    private static final Color CONSOLE_NOT_COVERED = new Color(199, 0, 1);

    private JaCoCoReportAnalyzer() {
    }

    /**
     * Connect to a remote JaCoCo Java Agent and collect coverage data. The remote agent has to run in output mode {@code tcpserver}
     * and request execution data.
     * <br/>See <a href="http://www.eclemma.org/jacoco/trunk/doc/agent.html">JaCoCo agent configuration</a>.
     * <br/>See <a href="http://www.eclemma.org/jacoco/trunk/doc/examples/java/ExecutionDataClient.java">TCP client example code</a>.
     *
     * @param address coverage agent's TCP address (JaCoCo agent default address is loopback interface, aka localhost).
     * @param port coverage agent's TCP port (JaCoCo agent default port is 6300).
     * @param jacocoexec the binary report to dump collected coverage data to.
     * @param prjClassesDir directory containing project's compiled classes.
     * @param prjSourcesDir the directory containing project's Java source files.
     */
    public static void tcpToBinary(String address, int port, File jacocoexec, File prjClassesDir, File prjSourcesDir) {
        // TODO dump coverage data from remote agent (scheduled for 1.2.0 or later)
    }

    /**
     * Load a JaCoCo binary report and convert it to HTML.
     * <br/>See <a href="http://www.eclemma.org/jacoco/trunk/doc/examples/java/ReportGenerator.java">report generator example code</a>.
     *
     * @param jacocoexec the JaCoCo binary report.
     * @param reportdir the folder to store HTML report.
     * @param prjClassesDir the directory containing project's compiled classes.
     * @param prjSourcesDir the directory containing project's Java source files.
     * @return the absolute path of HTML report's {@code index.html} file.
     * @throws FileNotFoundException if the JaCoCo binary report, compiled classes or Java sources files directory can't be found.
     * @throws IOException if an I/O error occurs.
     */
    public static String toHtmlReport(File jacocoexec, File reportdir, File prjClassesDir, File prjSourcesDir, String projectName)
            throws FileNotFoundException,
                   IOException {
        // Load the JaCoCo binary report.
        FileInputStream fis = new FileInputStream(jacocoexec);
        ExecutionDataStore executionDataStore = new ExecutionDataStore();
        SessionInfoStore sessionInfoStore = new SessionInfoStore();
        try {
            ExecutionDataReader executionDataReader = new ExecutionDataReader(fis);
            executionDataReader.setExecutionDataVisitor(executionDataStore);
            executionDataReader.setSessionInfoVisitor(sessionInfoStore);
            while (executionDataReader.read()) {
            }
        } finally {
            fis.close();
        }

        // Convert the binary report to HTML.
        CoverageBuilder coverageBuilder = new CoverageBuilder();
        Analyzer analyzer = new Analyzer(executionDataStore, coverageBuilder);
        analyzer.analyzeAll(prjClassesDir);
        IBundleCoverage bundleCoverage = coverageBuilder.getBundle("JaCoCoverage analysis of project \"" + projectName
                + "\" (powered by JaCoCo from EclEmma)");
        HTMLFormatter htmlformatter = new HTMLFormatter();
        IReportVisitor visitor = htmlformatter.createVisitor(new FileMultiReportOutput(reportdir));
        visitor.visitInfo(sessionInfoStore.getInfos(), executionDataStore.getContents());
        visitor.visitBundle(bundleCoverage, new DirectorySourceFileLocator(prjSourcesDir, DEF_ENCODING, 4));
        visitor.visitEnd();
        return new File(reportdir, "index.html").getAbsolutePath();
    }

    /**
     * Load a JaCoCo binary report and convert it to XML.
     * <br/>See <a href="http://www.eclemma.org/jacoco/trunk/doc/examples/java/ReportGenerator.java">report generator example code</a>.
     *
     * @param jacocoexec the JaCoCo binary report.
     * @param xmlreport the XML file to generate.
     * @param prjClassesDir the directory containing project's compiled classes.
     * @param prjSourcesDir the directory containing project's Java source files.
     * @throws FileNotFoundException if the JaCoCo binary report, compiled classes or Java sources files directory can't be found.
     * @throws IOException if an I/O error occurs.
     */
    public static void toXmlReport(File jacocoexec, File xmlreport, File prjClassesDir, File prjSourcesDir)
            throws FileNotFoundException,
                   IOException {
        // Load the JaCoCo binary report.
        FileInputStream fis = new FileInputStream(jacocoexec);
        ExecutionDataStore executionDataStore = new ExecutionDataStore();
        SessionInfoStore sessionInfoStore = new SessionInfoStore();
        try {
            ExecutionDataReader executionDataReader = new ExecutionDataReader(fis);
            executionDataReader.setExecutionDataVisitor(executionDataStore);
            executionDataReader.setSessionInfoVisitor(sessionInfoStore);
            while (executionDataReader.read()) {
            }
        } finally {
            fis.close();
        }

        // Convert the binary report to XML.
        CoverageBuilder coverageBuilder = new CoverageBuilder();
        Analyzer analyzer = new Analyzer(executionDataStore, coverageBuilder);
        analyzer.analyzeAll(prjClassesDir);
        IBundleCoverage bundleCoverage = coverageBuilder.getBundle("JaCoCoverage analysis (powered by JaCoCo from EclEmma)");
        XMLFormatter xmlformatter = new XMLFormatter();
        xmlformatter.setOutputEncoding(DEF_ENCODING);
        IReportVisitor visitor = xmlformatter.createVisitor(new FileOutputStream(xmlreport));
        visitor.visitInfo(sessionInfoStore.getInfos(), executionDataStore.getContents());
        visitor.visitBundle(bundleCoverage, new DirectorySourceFileLocator(prjSourcesDir, DEF_ENCODING, 4));
        visitor.visitEnd();
    }

    /**
     * Load JaCoCo coverage data and show it to a NetBeans console tab.
     *
     * @param coverageData the JaCoCo coverage data to show.
     * @param tabName the name of the NetBeans console tab to open.
     */
    public static void toConsoleReport(Map<String, JavaClass> coverageData, String tabName)
            throws IOException {
        InputOutput io = IOProvider.getDefault().getIO(tabName, false);
        try {
            io.getOut().reset();
            IOColorPrint.print(io, "=== JaCoCoverage report (powered by JaCoCo from EclEmma) ===\n", Color.GRAY);
            IOColorPrint.print(io, "Covered | Partially covered | Not covered | Java Class\n\n", Color.GRAY);
            List<JavaClass> sortedClasses = new ArrayList<JavaClass>(coverageData.values());
            Collections.sort(sortedClasses);
            for (JavaClass jclass : sortedClasses) {
                IOColorPrint.print(io, String.format("%5s", jclass.getCoveredLines().size()), CONSOLE_COVERED);
                IOColorPrint.print(io, " " + String.format("%5s", jclass.getPartiallyCoveredLines().size()), CONSOLE_PARTIALLY_COVERED);
                IOColorPrint.print(io, " " + String.format("%5s", jclass.getNotCoveredLines().size()), CONSOLE_NOT_COVERED);
                Color classCovColor;
                io.getOut().print("    " + jclass.getPackageName());
                boolean existCL = !jclass.getCoveredLines().isEmpty();
                boolean existPCL = !jclass.getPartiallyCoveredLines().isEmpty();
                boolean existNCL = !jclass.getNotCoveredLines().isEmpty();
                if (existCL) {
                    if (existNCL || existPCL) {
                        classCovColor = CONSOLE_PARTIALLY_COVERED;
                    } else {
                        classCovColor = CONSOLE_COVERED;
                    }
                } else {
                    if (existPCL) {
                        classCovColor = CONSOLE_PARTIALLY_COVERED;
                    } else {
                        classCovColor = CONSOLE_NOT_COVERED;
                    }
                }
                IOColorPrint.print(io, jclass.getClassName() + '\n', classCovColor);
            }
            IOColorPrint.print(io, "\nEnd of report\n", Color.GRAY);
            IOColorPrint.print(io, "You can ask questions and report bugs by visiting: ", Color.GRAY);
            IOColorPrint.print(io, "https://github.com/jonathanlermitage/tikione-jacocoverage", Color.BLUE);
            io.getOut().println();
        } finally {
            io.getOut().close();
        }
    }
}
