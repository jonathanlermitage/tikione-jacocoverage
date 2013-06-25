package fr.tikione.jacocoverage.plugin.config;

import javax.swing.JComponent;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.netbeans.spi.project.ui.support.ProjectCustomizer.Category;
import org.openide.util.Lookup;

@ProjectCustomizer.CompositeCategoryProvider.Registration(
        projectType = "org-netbeans-modules-java-j2seproject",
        position = 1000)
public class ProjectCustomizerAntJavase implements ProjectCustomizer.CompositeCategoryProvider {

    public @Override
    Category createCategory(Lookup context) {
        return ProjectCustomizer.Category.create(
                "JaCoCoverage",
                "JaCoCoverage",
                null);
    }

    public @Override
    JComponent createComponent(Category category, Lookup context) {
        return new JPanelPrjcfgAntJavase();
    }
}
