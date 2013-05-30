package fr.tikione.jacocoverage.plugin.anno;

/**
 * Coverage annotation.
 *
 * @author Jonathan Lermitage
 */
public class CoverageAnnotation extends AbstractCoverageAnnotation {

    private final EditorCoverageStateEnum state;

    public CoverageAnnotation(EditorCoverageStateEnum state, String projectName, String classFullName, Integer lineNum) {
        super(projectName, classFullName, lineNum);
        this.state = state;
    }

    @Override
    public String getAnnotationType() {
        return state.getType();
    }

    @Override
    public String getShortDescription() {
        return state.getDescription();
    }
}
