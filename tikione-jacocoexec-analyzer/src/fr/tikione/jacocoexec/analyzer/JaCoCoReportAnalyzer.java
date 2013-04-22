package fr.tikione.jacocoexec.analyzer;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.data.ExecutionDataReader;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.jacoco.report.DirectorySourceFileLocator;
import org.jacoco.report.IReportVisitor;
import org.jacoco.report.xml.XMLFormatter;
import org.openide.windows.IOColorPrint;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

/**
 * JaCoCo reports related utilities.
 *
 * @author Jonathan Lermitage
 */
public class JaCoCoReportAnalyzer {

    private static Color CONSOLE_COVERED = new Color(44, 126, 0);

    private static Color CONSOLE_PARTIALLY_COVERED = new Color(186, 93, 0);

    private static Color CONSOLE_NOT_COVERED = new Color(199, 0, 1);

    private JaCoCoReportAnalyzer() {
    }

    /**
     * Load a JaCoCo binary report and convert it to XML.
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
        xmlreport.delete();

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
        IBundleCoverage bundleCoverage = coverageBuilder.getBundle("JaCoCoverage analysis, powered by JaCoCo");
        XMLFormatter xmlformatter = new XMLFormatter();
        xmlformatter.setOutputEncoding("UTF-8");
        IReportVisitor visitor = xmlformatter.createVisitor(new FileOutputStream(xmlreport));
        visitor.visitInfo(sessionInfoStore.getInfos(), executionDataStore.getContents());
        visitor.visitBundle(bundleCoverage, new DirectorySourceFileLocator(prjSourcesDir, "UTF-8", 4));
        visitor.visitEnd();
    }

    /**
     * Load JaCoCo coverage data and show it to the NetBeans console.
     *
     * @param xmlreport the XML report file to load.
     * @return the textual report.
     */
    public static void toConsoleReport(List<JavaClass> coverageData)
            throws IOException {
        InputOutput io = IOProvider.getDefault().getIO("JaCoCoverage Report", false);
        try {
            io.getOut().reset(); //
            IOColorPrint.print(io, "=== JaCoCoverage report (powered by JaCoCo from EclEmma) ===\n", Color.GRAY);
            IOColorPrint.print(io, "Covered | Partially covered | Not covered | Java Class\n\n", Color.GRAY);
            for (JavaClass jclass : coverageData) {
                IOColorPrint.print(io, String.format("%5s", jclass.getCoveredLines().size()), CONSOLE_COVERED);
                IOColorPrint.print(io, " " + String.format("%5s", jclass.getPartiallyCoveredLines().size()), CONSOLE_PARTIALLY_COVERED);
                IOColorPrint.print(io, " " + String.format("%5s", jclass.getNotCoveredLines().size()), CONSOLE_NOT_COVERED);
                io.getOut().println("    " + jclass.getPackageName() + jclass.getClassName());
            }
            IOColorPrint.print(io, "\nEnd of report\n\n", Color.GRAY);
            IOColorPrint.print(io, "TikiOne JaCoCoverage " + Version.VERSION
                    + ", TikiOne JaCoCo Reporting Library " + fr.tikione.jacocoexec.analyzer.Version.VERSION
                    + ", EclEmma JaCoCo Library " + fr.tikione.jacoco.lib.Version.VERSION + "\n", Color.GRAY);
            IOColorPrint.print(io, "You can ask questions and report bugs by visiting: "
                    + "https://github.com/jonathanlermitage/tikione-jacocoverage", Color.GRAY);
        } finally {
            io.getOut().close();
        }
    }
}
