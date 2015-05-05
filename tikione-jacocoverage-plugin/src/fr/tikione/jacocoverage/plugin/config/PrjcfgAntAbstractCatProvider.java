package fr.tikione.jacocoverage.plugin.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.netbeans.spi.project.ui.support.ProjectCustomizer;
import org.netbeans.spi.project.ui.support.ProjectCustomizer.Category;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

/**
 * JaCoCoverage configuration category at project level.
 * <br/>See <a href="http://wiki.netbeans.org/DevFaqActionAddProjectCustomizer">DevFaqActionAddProjectCustomizer</a> for integration.
 *
 * @author Jonathan Lermitage
 */
public class PrjcfgAntAbstractCatProvider implements ProjectCustomizer.CompositeCategoryProvider {

    private Category cat;

    public @Override
    Category createCategory(Lookup context) {
        ImageIcon pluginIcon = new ImageIcon(getClass().getResource(
                "/fr/tikione/jacocoverage/plugin/resources/icon/famfamfam_lightning.png"));
        cat = ProjectCustomizer.Category.create(
                "JaCoCoverage",
                "JaCoCoverage",
                pluginIcon.getImage());
        return cat;
    }

    public @Override
    JComponent createComponent(Category category, Lookup context) {
        JPanel panel;
        try {
            panel = new PrjcfgAntJavasePanel(context);
            cat.setStoreListener(new PrjcfgAntJavaseListener((IStorable) panel));
        } catch (FileNotFoundException ex) {
            Exceptions.printStackTrace(ex);
            panel = new JPanel();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            panel = new JPanel();
        }
        return panel;
    }
}
