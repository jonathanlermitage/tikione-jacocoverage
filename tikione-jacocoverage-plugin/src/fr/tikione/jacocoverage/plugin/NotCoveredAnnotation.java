package fr.tikione.jacocoverage.plugin;

import org.openide.text.Annotation;

/**
 * Coverage annotation: not covered code.
 * See https://blogs.oracle.com/geertjan/entry/creating_error_annotations_in_netbeans2
 *
 * @author Jonathan Lermitage
 */
public class NotCoveredAnnotation extends Annotation {

    static final NotCoveredAnnotation DEFAULT = new NotCoveredAnnotation();

    @Override
    public String getAnnotationType() {
        return "annotation_notcovered";
    }

    @Override
    public String getShortDescription() {
        return "Not covered";
    }
}
