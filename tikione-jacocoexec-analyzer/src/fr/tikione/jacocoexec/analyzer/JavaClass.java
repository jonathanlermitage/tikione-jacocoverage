package fr.tikione.jacocoexec.analyzer;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a Java class code coverage.
 *
 * @author Jonathan Lermitage
 */
public class JavaClass {

    /** The package name (with "/" instead of "."). */
    private String packageName;

    /** The class name (with ".java" extension). */
    private String className;

    /** The coverage status of the entire Java class. */
    private CoverageStatus coverageStatus;

    /** Indicate which lines or source code are fully covered. */
    private final List<Integer> coveredLines = new ArrayList<Integer>(16);

    /** Indicate which lines or source code are partially covered. */
    private final List<Integer> partiallyCoveredLines = new ArrayList<Integer>(16);

    /** Indicate which lines or source code are not covered. */
    private final List<Integer> notCoveredLines = new ArrayList<Integer>(16);

    /**
     * Describe a Java class. Coverage data will be added later.
     *
     * @param packageName the package name.
     * @param className the class name.
     */
    public JavaClass(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }

    public void addCoveredLine(int lineNumber) {
        coveredLines.add(lineNumber);
    }

    public void addPartiallyCoveredLine(int lineNumber) {
        partiallyCoveredLines.add(lineNumber);
    }

    public void addNotCoveredLine(int lineNumber) {
        notCoveredLines.add(lineNumber);
    }

    public void setCoverageStatus(CoverageStatus coverageStatus) {
        this.coverageStatus = coverageStatus;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public List<Integer> getCoveredLines() {
        return coveredLines;
    }

    public List<Integer> getPartiallyCoveredLines() {
        return partiallyCoveredLines;
    }

    public List<Integer> getNotCoveredLines() {
        return notCoveredLines;
    }

    public CoverageStatus getCoverageStatus() {
        return coverageStatus;
    }
}
