package fr.tikione.jacocoexec.analyzer;

import java.util.HashMap;
import java.util.Map;

/**
 * Representation of a Java class code coverage.
 *
 * @author Jonathan Lermitage
 */
@lombok.RequiredArgsConstructor(staticName = "build")
public class JavaClass implements Comparable<JavaClass> {

    /** The package name (with "/" instead of "."). */
    @lombok.Getter(lombok.AccessLevel.PUBLIC)
    @lombok.NonNull()
    private String packageName;

    /** The class name (with ".java" extension). */
    @lombok.Getter(lombok.AccessLevel.PUBLIC)
    @lombok.NonNull()
    private String className;

    /** Indicate the coverage state of classe's instructions. */
    @lombok.Getter(lombok.AccessLevel.PUBLIC)
    private final Map<Integer, CoverageStateEnum> coverage = new HashMap<Integer, CoverageStateEnum>(256);

    /** Indicate the coverage description of classe's instructions. */
    @lombok.Getter(lombok.AccessLevel.PUBLIC)
    private final Map<Integer, String> coverageDesc = new HashMap<Integer, String>(256);

    /** Indicate the coverage state of classe's methods declarations. */
    @lombok.Getter(lombok.AccessLevel.PUBLIC)
    private final Map<Integer, CoverageStateEnum> methodCoverage = new HashMap<Integer, CoverageStateEnum>(24);

    /** Number of covered lines. */
    @lombok.Getter(lombok.AccessLevel.PUBLIC)
    private int nbCoveredLines = 0;

    /** Number of partially covered lines. */
    @lombok.Getter(lombok.AccessLevel.PUBLIC)
    private int nbPartiallyCoveredLines = 0;

    /** Number of not covered lines. */
    @lombok.Getter(lombok.AccessLevel.PUBLIC)
    private int nbNotCoveredLines = 0;

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
    
    @Override
    public int compareTo(JavaClass o) {
        return (this.getPackageName() + this.getClassName()).compareTo(o.getPackageName() + o.getClassName());
    }
}
