package fr.tikione.jacocoexec.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representation of a Java class code coverage.
 *
 * @author Jonathan Lermitage
 */
public class JavaClass implements Comparable<JavaClass> {

    /** The package name (with "/" instead of "."). */
    private String packageName;

    /** The class name (with ".java" extension). */
    private String className;

    /** Indicate which lines or source code are fully covered. */
    private final List<Integer> coveredLines = new ArrayList<Integer>(64);

    /** Indicate which lines or source code are partially covered. */
    private final List<Integer> partiallyCoveredLines = new ArrayList<Integer>(32);

    /** Indicate which lines or source code are not covered. */
    private final List<Integer> notCoveredLines = new ArrayList<Integer>(32);

    /** Indicate the coverage state of classe's methods declarations. */
    private final Map<Integer, CoverageStateEnum> methodCoverage = new HashMap<Integer, CoverageStateEnum>(24);

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

    public void addMethodCoverage(int lineNumber, CoverageStateEnum coverageState) {
        methodCoverage.put(lineNumber, coverageState);
    }

    public Map<Integer, CoverageStateEnum> getMethodCoverage() {
        return methodCoverage;
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

    @Override
    public int compareTo(JavaClass o) {
        return (this.getPackageName() + this.getClassName()).compareTo(o.getPackageName() + o.getClassName());
    }
}
