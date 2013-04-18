package fr.tikione.jacocoverage.plugin;

import org.openide.text.Annotation;

/**
 * Coverage annotation: partially covered code.
 * See https://blogs.oracle.com/geertjan/entry/creating_error_annotations_in_netbeans2
 *
 * @author Jonathan Lermitage
 */
public class PartiallyCoveredAnnotation extends Annotation {

    static final PartiallyCoveredAnnotation DEFAULT = new PartiallyCoveredAnnotation();

    @Override
    public String getAnnotationType() {
        return "annotation_partiallycovered";
    }

    @Override
    public String getShortDescription() {
        return "Partially covered";
    }
}
