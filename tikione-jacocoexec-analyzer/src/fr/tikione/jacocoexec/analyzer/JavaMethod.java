package fr.tikione.jacocoexec.analyzer;

/**
 * Representation of a Java method coverage.
 *
 * @author Jonathan Lermitage
 */
@lombok.Data
public class JavaMethod {

    /** Method's name. */
    private String name;

    /** Method's coverage state. */
    private CoverageStateEnum coverageState;

    /** Number of covered instructions. */
    private int instructionsCovered = 0;

    /** Number of not covered instructions. */
    private int instructionsMissed = 0;

    /** Number of covered lines. */
    private int linesCovered = 0;

    /** Number of not covered lines. */
    private int linesMissed = 0;

    /** Description of method's coverage state. */
    private String coverageDesc;

    /** 
     * Line number of method's declaration in NetBeans source code editor.
     * Warning: in NetBeans editor starting index is 0, not 1. */
    private int lineNumber;

}
