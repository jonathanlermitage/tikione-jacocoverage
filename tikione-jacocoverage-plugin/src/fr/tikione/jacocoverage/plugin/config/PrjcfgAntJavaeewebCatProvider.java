package fr.tikione.jacocoverage.plugin.config;

import org.netbeans.spi.project.ui.support.ProjectCustomizer;

/**
 * JaCoCoverage configuration category at project level.
 * <br/>See <a href="http://wiki.netbeans.org/DevFaqActionAddProjectCustomizer">DevFaqActionAddProjectCustomizer</a> for integration.
 *
 * @author Jonathan Lermitage
 */
@ProjectCustomizer.CompositeCategoryProvider.Registrations({
	@ProjectCustomizer.CompositeCategoryProvider.Registration(
			projectType = "org-netbeans-modules-web-project",
			position = 1405),
	@ProjectCustomizer.CompositeCategoryProvider.Registration(
			projectType = "org-netbeans-modules-j2ee",
			position = 1406)
})
public class PrjcfgAntJavaeewebCatProvider extends PrjcfgAntAbstractCatProvider implements ProjectCustomizer.CompositeCategoryProvider {
}
