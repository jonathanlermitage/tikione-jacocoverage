package fr.tikione.jacocoexec.analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.data.ExecutionDataReader;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.jacoco.report.DirectorySourceFileLocator;
import org.jacoco.report.IReportVisitor;
import org.jacoco.report.xml.XMLFormatter;

/**
 * JaCoCo reports related utilities.
 *
 * @author Jonathan Lermitage
 */
public class JaCoCoReportAnalyzer {

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
    public static void createXmlReport(File jacocoexec, File xmlreport, File prjClassesDir, File prjSourcesDir)
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
}
