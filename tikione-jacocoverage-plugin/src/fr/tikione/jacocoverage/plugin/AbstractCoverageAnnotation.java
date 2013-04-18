package fr.tikione.jacocoverage.plugin;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Iterator;
import org.openide.text.Annotatable;
import org.openide.text.Annotation;
import org.openide.text.Line;

/**
 *
 * 
 * @author Jonathan Lermitage
 */
public class AbstractCoverageAnnotation extends Annotation implements PropertyChangeListener {

    private final static HashMap<String, HashMap<Integer, Annotation>> annotations = new HashMap<String, HashMap<Integer, Annotation>>();

    private String descrmessage = null;

    private final static String KEY_JOIN_CHAR = " ";

    /**
     * Creates new annotation and adds it to the global annotations list so it can be managed later.
     * 
     * @param projectName Project this annotation belongs to
     * @param className Class this annotation belongs to
     * @param lineNum Number of the line to be annotated
     */
    public AbstractCoverageAnnotation(String projectName, String className, Integer lineNum) {
        synchronized (annotations) {
            String key = combineKey(projectName, className);
            HashMap<Integer, Annotation> anns = annotations.get(key);
            if (anns == null) {
                anns = new HashMap<Integer, Annotation>();
                annotations.put(key, anns);
            }
            anns.put(lineNum, this);
        }
    }

    /**
     * Removes all editor annotations from specified project.
     * Currently uses slow string comparing approach to select annotations maps for current project.
     * Might be changed to use more complicated storage structure, but it will make annotations storing more difficult.
     *
     * @param projectName Project to remove annotations for
     */
    public static void removeAll(String projectName) {
        synchronized (annotations) {
            for (Iterator<String> it = annotations.keySet().iterator(); it.hasNext();) {
                String key = it.next();
                if (key.startsWith(projectName + KEY_JOIN_CHAR)) {
                    HashMap<Integer, Annotation> anns = annotations.get(key);
                    for (Iterator<Annotation> lit = anns.values().iterator(); lit.hasNext();) {
                        lit.next().detach();
                    }
                    anns.clear();
                    it.remove();
                }
            }
        }
    }

    /**
     * Removes annotations from specific class in the specific project
     * @param projectName Project to remove annotations for
     * @param className Class to remove annotations for
     */
    public static void removeFromClass(String projectName, String className) {
        String id = combineKey(projectName, className);
        synchronized (annotations) {
            HashMap<Integer, Annotation> anns = annotations.get(id);
            if (anns != null) {
                for (Iterator<Annotation> lit = anns.values().iterator(); lit.hasNext();) {
                    lit.next().detach();
                }
                anns.clear();
                annotations.remove(id);
            }
        }
    }

    public void setMessage(String message) {
        descrmessage = message;
    }

    public String getMessage() {
        return descrmessage;
    }

    /**
     * implemented  from PropertyChangeListener.
     * Detaches itself as annotation and property change listener
     * on all actions except
     * Annotatable.PROP_ANNOTATION_COUNT (e.g.: adding/removing breakpoint)
     */
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName() == null
                ? Annotatable.PROP_ANNOTATION_COUNT != null
                : !propertyChangeEvent.getPropertyName().equals(Annotatable.PROP_ANNOTATION_COUNT)) {
            Line line = (Line) propertyChangeEvent.getSource();
            line.removePropertyChangeListener(this);
            detach();
            annotations.remove(this);
        }
    }

    /**
     *This method is used to create a unique key for every new class' annotation
     **/
    private static String combineKey(String projectName, String className) {
        return projectName + KEY_JOIN_CHAR + className;
    }

    @Override
    public String getAnnotationType() {
        return "";
    }

    @Override
    public String getShortDescription() {
        return "";
    }
}
