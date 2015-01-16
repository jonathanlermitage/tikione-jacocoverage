package fr.tikione.jacocoexec.analyzer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.tools.ExecFileLoader;
import org.jacoco.report.DirectorySourceFileLocator;
import org.jacoco.report.FileMultiReportOutput;
import org.jacoco.report.IReportGroupVisitor;
import org.jacoco.report.IReportVisitor;
import org.jacoco.report.MultiSourceFileLocator;
import org.jacoco.report.html.HTMLFormatter;
import org.jacoco.report.xml.XMLFormatter;

/**
 * @author Graeme Ingleby
 */
public class JacocoNBModuleReportGenerator {

	private static final String DEF_ENCODING = "UTF-8";

	private final IReportVisitor visitor;
	private final IReportGroupVisitor groupVisitor;
	private final File executionDataFile;
	private final ExecFileLoader execFileLoader;

	public JacocoNBModuleReportGenerator(File executationDataFile, File reportdir, boolean xmlReport) throws IOException {
		this.executionDataFile = executationDataFile;
		execFileLoader = new ExecFileLoader();
		execFileLoader.load(executionDataFile);

		if (xmlReport) {
			XMLFormatter xmlformatter = new XMLFormatter();
			xmlformatter.setOutputEncoding(DEF_ENCODING);
			visitor = xmlformatter.createVisitor(new FileOutputStream(reportdir));
		} else {
			HTMLFormatter htmlFormater = new HTMLFormatter();
			visitor = htmlFormater.createVisitor(new FileMultiReportOutput(reportdir));
		}
		visitor.visitInfo(execFileLoader.getSessionInfoStore().getInfos(), execFileLoader.getExecutionDataStore().getContents());
		groupVisitor = visitor.visitGroup("JaCoCo Coverage Report");
	}

	public void processNBModule(String projectName, List<String> classDirectories, List<String> sourceDirectories) throws IOException {
		CoverageBuilder coverageBuilder = new CoverageBuilder();
		Analyzer analyzer = new Analyzer(execFileLoader.getExecutionDataStore(), coverageBuilder);

		for (String classDirectory : classDirectories) {
			analyzer.analyzeAll(new File(classDirectory));
		}

		IBundleCoverage bundleCoverage = coverageBuilder.getBundle(projectName);

		MultiSourceFileLocator sourceLocator = new MultiSourceFileLocator(4);
		for (String sourceDirectory : sourceDirectories) {
			sourceLocator.add(new DirectorySourceFileLocator(new File(sourceDirectory), DEF_ENCODING, 4));
		}

		groupVisitor.visitBundle(bundleCoverage, sourceLocator);
	}

	public void end() throws IOException {
		visitor.visitEnd();
	}
}
