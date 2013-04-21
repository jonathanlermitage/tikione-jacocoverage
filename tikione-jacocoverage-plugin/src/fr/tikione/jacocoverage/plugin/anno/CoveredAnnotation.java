package fr.tikione.jacocoverage.plugin.anno;

import org.openide.text.Annotation;

/**
 * Coverage annotation: covered code.
 * See https://blogs.oracle.com/geertjan/entry/creating_error_annotations_in_netbeans2
 *
 * @author Jonathan Lermitage
 */
public class CoveredAnnotation extends Annotation {

    static final CoveredAnnotation DEFAULT = new CoveredAnnotation();

    @Override
    public String getAnnotationType() {
        return "annotation_covered";
    }

    @Override
    public String getShortDescription() {
        return "Covered";
    }
}
