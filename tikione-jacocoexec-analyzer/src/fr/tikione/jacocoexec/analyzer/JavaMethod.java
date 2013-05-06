package fr.tikione.jacocoexec.analyzer;

/**
 * Representation of a Java method coverage.
 *
 * @author Jonathan Lermitage
 */
public class JavaMethod {

    private CoverageStateEnum coverageState;

    private int instructionsCovered = 0;

    private int instructionsMissed = 0;

    private int linesCovered = 0;

    private int linesMissed = 0;

    private String coderageDesc;

    public JavaMethod() {
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

    public String getCoderageDesc() {
        return coderageDesc;
    }

    public void setCoderageDesc(String coderageDesc) {
        this.coderageDesc = coderageDesc;
    }
}
