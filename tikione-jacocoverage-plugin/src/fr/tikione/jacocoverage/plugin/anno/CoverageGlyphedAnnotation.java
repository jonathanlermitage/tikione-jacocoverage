package fr.tikione.jacocoverage.plugin.anno;

/**
 * Coverage glyphed annotation.
 *
 * @author Jonathan Lermitage
 */
public class CoverageGlyphedAnnotation extends CoverageAnnotation {

    private final String desc;

    public CoverageGlyphedAnnotation(EditorCoverageStateEnum state, String projectName, String classFullName, Integer lineNum, String desc) {
        super(state, projectName, classFullName, lineNum);
        this.desc = desc;
    }

    @Override
    public String getAnnotationType() {
        return super.getAnnotationType() + "_glyph";
    }

    @Override
    public String getShortDescription() {
        return desc;
    }
}
