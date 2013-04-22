package fr.tikione.jacocoverage.plugin.anno;

/**
 * Coverage status.
 *
 * @author Jonathan Lermitage
 */
public enum CoverageStateEnum {

    /** Covered. */
    COVERED,
    /** Partially covered. */
    PARTIALLY_COVERED,
    /** Not covered. */
    NOT_COVERED;

    public String getDescription() {
        switch (this) {
            case COVERED:
                return "Covered";
            case PARTIALLY_COVERED:
                return "Partially covered";
            case NOT_COVERED:
                return "Not covered";
            default:
                return "Unknown info about coverage";
        }
    }

    public String getType() {
        switch (this) {
            case COVERED:
                return "annotation_covered";
            case PARTIALLY_COVERED:
                return "annotation_partiallycovered";
            case NOT_COVERED:
                return "annotation_notcovered";
            default:
                return "annotation_unknown";
        }
    }
}
