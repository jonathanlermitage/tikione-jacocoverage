package fr.tikione.jacocoverage.plugin.config;

import javax.swing.JComponent;
import javax.swing.JPanel;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.netbeans.spi.project.ui.support.ProjectCustomizer.Category;
import org.openide.util.Lookup;

/**
 * JaCoCoverage configuration at project's level.
 * <br/>See <a href="http://wiki.netbeans.org/DevFaqActionAddProjectCustomizer">DevFaqActionAddProjectCustomizer</a> for integration.
 *
 * @author Jonathan Lermitage
 */
@ProjectCustomizer.CompositeCategoryProvider.Registration(
        projectType = "org-netbeans-modules-java-j2seproject",
        position = 1400)
public class ProjectCustomizerAntJavase implements ProjectCustomizer.CompositeCategoryProvider {

    private Category cat;

    public @Override
    Category createCategory(Lookup context) {
        cat = ProjectCustomizer.Category.create(
                "JaCoCoverage",
                "JaCoCoverage",
                null);
        return cat;
    }

    public @Override
    JComponent createComponent(Category category, Lookup context) {
        JPanel panel = new JPanelPrjcfgAntJavase(context);
        cat.setStoreListener(new PrjcfgAntJavaseListener((IStorable) panel));
        return panel;
    }
}
