package fr.tikione.jacocoverage.plugin.config;

import org.netbeans.spi.project.ui.support.ProjectCustomizer;

/**
 * JaCoCoverage configuration category at project level.
 * <br/>See <a href="http://wiki.netbeans.org/DevFaqActionAddProjectCustomizer">DevFaqActionAddProjectCustomizer</a> for integration.
 *
 * @author Jonathan Lermitage
 */
@ProjectCustomizer.CompositeCategoryProvider.Registration(
        projectType = "org-netbeans-modules-web-project-webproject",
        position = 1405)
public class PrjcfgAntJavaeewebCatProvider extends PrjcfgAntAbstractCatProvider implements ProjectCustomizer.CompositeCategoryProvider {
}
