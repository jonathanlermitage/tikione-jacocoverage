package fr.tikione.jacocoexec.analyzer;

import java.util.HashMap;
import java.util.Map;

/**
 * Representation of a Java class code coverage.
 *
 * @author Jonathan Lermitage
 */
public class JavaClass implements Comparable<JavaClass> {

    /** The package name (with "/" instead of "."). */
    private final String packageName;

    /** The class name (with ".java" extension). */
    private final String className;

    /** Indicate the coverage state of class instructions. */
    private final Map<Integer, CoverageStateEnum> coverage = new HashMap<Integer, CoverageStateEnum>(256);

    /** Indicate the coverage description of class instructions. */
    private final Map<Integer, String> coverageDesc = new HashMap<Integer, String>(128);

    /** Indicate the coverage state of class methods declarations. */
    private final Map<Integer, CoverageStateEnum> methodCoverage = new HashMap<Integer, CoverageStateEnum>(32);

    /** Number of covered lines. */
    private int nbCoveredLines = 0;

    /** Number of partially covered lines. */
    private int nbPartiallyCoveredLines = 0;

    /** Number of not covered lines. */
    private int nbNotCoveredLines = 0;

    public JavaClass(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }

    public void addCoveredLine(int lineNumber) {
        coverage.put(lineNumber, CoverageStateEnum.COVERED);
        nbCoveredLines++;
    }

    public void addPartiallyCoveredLine(int lineNumber) {
        coverage.put(lineNumber, CoverageStateEnum.PARTIALLY_COVERED);
        nbPartiallyCoveredLines++;
    }

    public void addNotCoveredLine(int lineNumber) {
        coverage.put(lineNumber, CoverageStateEnum.NOT_COVERED);
        nbNotCoveredLines++;
    }

    public void addMethodCoverage(int lineNumber, CoverageStateEnum coverageState) {
        methodCoverage.put(lineNumber, coverageState);
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public Map<Integer, CoverageStateEnum> getCoverage() {
        return coverage;
    }

    public Map<Integer, String> getCoverageDesc() {
        return coverageDesc;
    }

    public Map<Integer, CoverageStateEnum> getMethodCoverage() {
        return methodCoverage;
    }

    public int getNbCoveredLines() {
        return nbCoveredLines;
    }

    public int getNbPartiallyCoveredLines() {
        return nbPartiallyCoveredLines;
    }

    public int getNbNotCoveredLines() {
        return nbNotCoveredLines;
    }
    
    @Override
    public int compareTo(JavaClass o) {
        return (this.getPackageName() + this.getClassName()).compareTo(o.getPackageName() + o.getClassName());
    }
}
