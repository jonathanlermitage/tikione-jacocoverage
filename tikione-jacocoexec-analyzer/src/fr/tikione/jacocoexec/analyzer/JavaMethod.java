package fr.tikione.jacocoexec.analyzer;

/**
 * Representation of a Java method coverage.
 *
 * @author Jonathan Lermitage
 */
@lombok.NoArgsConstructor
public class JavaMethod {

    @lombok.Getter(lombok.AccessLevel.PUBLIC)
    @lombok.Setter(lombok.AccessLevel.PUBLIC)
    private CoverageStateEnum coverageState;

    @lombok.Getter(lombok.AccessLevel.PUBLIC)
    @lombok.Setter(lombok.AccessLevel.PUBLIC)
    private int instructionsCovered = 0;

    @lombok.Getter(lombok.AccessLevel.PUBLIC)
    @lombok.Setter(lombok.AccessLevel.PUBLIC)
    private int instructionsMissed = 0;

    @lombok.Getter(lombok.AccessLevel.PUBLIC)
    @lombok.Setter(lombok.AccessLevel.PUBLIC)
    private int linesCovered = 0;

    @lombok.Getter(lombok.AccessLevel.PUBLIC)
    @lombok.Setter(lombok.AccessLevel.PUBLIC)
    private int linesMissed = 0;

    @lombok.Getter(lombok.AccessLevel.PUBLIC)
    @lombok.Setter(lombok.AccessLevel.PUBLIC)
    private String coderageDesc;
}
