package fr.tikione.jacocoverage.plugin.action;

import fr.tikione.jacocoexec.analyzer.JacocoNBModuleReportGenerator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.spi.project.SubprojectProvider;
import org.openide.util.Exceptions;

/**
 * @author Graeme Ingleby
 */
public class NBJaCoCoExtension {

	private JacocoNBModuleReportGenerator reportGenerator;

	public static String toHTMLReport(File binreport, File reportdir, Project project) {
		return (new NBJaCoCoExtension()).myReportGeneration(binreport, reportdir, project);
	}

	public static void toXmlReport(File binreport, File reportfile, Project project) {
		(new NBJaCoCoExtension()).myXmlReportGeneration(binreport, reportfile, project);
	}

	/**
	 * XML Generation.
	 *
	 * @param jacocoExecFile
	 * @param reportdir
	 * @param project
	 */
	public void myXmlReportGeneration(File jacocoExecFile, File reportdir, Project project) {
		try {
			reportGenerator = new JacocoNBModuleReportGenerator(jacocoExecFile, reportdir, true);
			processProject(project);
			reportGenerator.end();
		} catch (IOException ex) {
			Exceptions.printStackTrace(ex);
		}
	}

	/**
	 * HTML Generation.
	 *
	 * @param jacocoExecFile
	 * @param reportdir
	 * @param project
	 * @return
	 */
	public String myReportGeneration(File jacocoExecFile, File reportdir, Project project) {
		try {
			reportGenerator = new JacocoNBModuleReportGenerator(jacocoExecFile, reportdir, false);
			processProject(project);
			reportGenerator.end();
		} catch (IOException ex) {
			Exceptions.printStackTrace(ex);
		}
		return new File(reportdir, "index.html").getAbsolutePath();
	}

	public void processProject(Project project) throws FileNotFoundException, IOException {
		List<String> moduleClassDirectories = new ArrayList<>(8);
		List<String> moduleSourceDirectories = new ArrayList<>(8);

		String prjDir = project.getProjectDirectory().getPath();

		File classDir = new File(prjDir + "/build/classes");
		if (classDir.exists()) {
			moduleClassDirectories.add(classDir.getPath());
		}

		Sources source = ProjectUtils.getSources(project);
		SourceGroup[] groups = source.getSourceGroups("java");
		for (SourceGroup group : groups) {
			moduleSourceDirectories.add(group.getRootFolder().getPath());
		}
		File genDir = new File(prjDir + "/build/classes-generated");
		if (genDir.exists()) {
			moduleSourceDirectories.add(genDir.getPath());
		}

		reportGenerator.processNBModule(project.getProjectDirectory().getName(), moduleClassDirectories, moduleSourceDirectories);
		processSubprojects(project);
	}

	public void processSubprojects(Project project) throws IOException {
		SubprojectProvider subs = project.getLookup().lookup(SubprojectProvider.class);
		if (subs != null) {
			Set<? extends Project> subProjects = subs.getSubprojects();
			for (Project p : subProjects) {
				processProject(p);
			}
		}
	}
}
