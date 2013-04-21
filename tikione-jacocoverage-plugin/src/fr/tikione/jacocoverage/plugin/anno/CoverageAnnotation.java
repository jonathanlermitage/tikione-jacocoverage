package fr.tikione.jacocoverage.plugin.anno;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Iterator;
import org.openide.text.Annotatable;
import org.openide.text.Annotation;
import org.openide.text.Line;

/**
 * Base annotation class that contains some basic logic and holds information about all annotations for all classes.
 * Based on the work of Andrey Korostelev (original author of "Code Coverage Plugin" based on EMMA for NetBeans 6).
 * See http://plugins.netbeans.org/plugin/5620/unit-tests-code-coverage-plugin for the NetBeans 6.0 EMMA based plugin.
 *
 * @author Andrey Korostelev 
 * @author Jonathan Lermitage
 */
public abstract class CoverageAnnotation extends Annotation implements PropertyChangeListener {

    private String descrmessage = null;

    private final static HashMap<String, HashMap<Integer, Annotation>> annotations = new HashMap<String, HashMap<Integer, Annotation>>();

    private final static String KEY_JOIN_CHAR = " ";

    /**
     * Creates new annotation and adds it to the global annotations list so it can be managed later
     *
     * @param projectName the project this annotation belongs to.
     * @param classFullName the class (package + name) this annotation belongs to.
     * @param lineNum the number of the line to be annotated.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public CoverageAnnotation(String projectName, String classFullName, Integer lineNum) {
        synchronized (annotations) {
            String key = combineKey(projectName, classFullName);
            HashMap<Integer, Annotation> anns = annotations.get(key);
            if (anns == null) {
                anns = new HashMap<Integer, Annotation>();
                annotations.put(key, anns);
            }
            anns.put(lineNum, this);
        }
    }

    @Override
    @SuppressWarnings("element-type-mismatch")
    public void propertyChange(PropertyChangeEvent evt) {
        // Detaches itself as annotation and property change listener on all actions except
        // Annotatable.PROP_ANNOTATION_COUNT (e.g.: adding/removing breakpoint).
        if (evt.getPropertyName() == null
                ? Annotatable.PROP_ANNOTATION_COUNT != null
                : !evt.getPropertyName().equals(Annotatable.PROP_ANNOTATION_COUNT)) {
            Line line = (Line) evt.getSource();
            line.removePropertyChangeListener(this);
            detach();
            annotations.remove(this);
        }
    }

    /**
     * Removes all editor annotations from specified project.
     * Currently uses slow string comparing approach to select annotations maps for current project. Might be changed to use more
     * complicated storage structure, but it will make annotations storing more difficult.
     *
     * @param projectName the project to remove annotations for.
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
     * Removes annotations from specific class in the specific project.
     *
     * @param projectName the project to remove annotations for.
     * @param classFullName the class (package + name) to remove annotations for.
     */
    public static void removeFromClass(String projectName, String classFullName) {
        String id = combineKey(projectName, classFullName);
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

    /**
     * Sets the current description message.
     *
     * @param message the description message.
     */
    public void setMessage(String message) {
        descrmessage = message;
    }

    /**
     * Getter for annotations description message.
     *
     * @return the description message.
     */
    public String getMessage() {
        return descrmessage;
    }

    /**
     * This method is used to create a unique key for every new class annotation.
     *
     * @param projectName the project this annotation belongs to.
     * @param classFullName the class (package + name) this annotation belongs to.
     */
    private static String combineKey(String projectName, String classFullName) {
        // FIXED replaced the class name by the package plus the class name, because class names are not unique.
        return projectName + KEY_JOIN_CHAR + classFullName;
    }
}
