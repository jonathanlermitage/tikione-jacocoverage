package fr.tikione.jacocoexec.analyzer;

/**
 * Representation of a Java method coverage.
 *
 * @author Jonathan Lermitage
 */
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

    public JavaMethod() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoverageStateEnum getCoverageState() {
        return coverageState;
    }

    public void setCoverageState(CoverageStateEnum coverageState) {
        this.coverageState = coverageState;
    }

    public int getInstructionsCovered() {
        return instructionsCovered;
    }

    public void setInstructionsCovered(int instructionsCovered) {
        this.instructionsCovered = instructionsCovered;
    }

    public int getInstructionsMissed() {
        return instructionsMissed;
    }

    public void setInstructionsMissed(int instructionsMissed) {
        this.instructionsMissed = instructionsMissed;
    }

    public int getLinesCovered() {
        return linesCovered;
    }

    public void setLinesCovered(int linesCovered) {
        this.linesCovered = linesCovered;
    }

    public int getLinesMissed() {
        return linesMissed;
    }

    public void setLinesMissed(int linesMissed) {
        this.linesMissed = linesMissed;
    }

    public String getCoverageDesc() {
        return coverageDesc;
    }

    public void setCoverageDesc(String coverageDesc) {
        this.coverageDesc = coverageDesc;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}
