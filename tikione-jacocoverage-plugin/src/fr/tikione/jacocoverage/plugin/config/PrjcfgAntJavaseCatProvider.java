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
			projectType = "org-netbeans-modules-java-j2seproject",
			position = 1400),
	@ProjectCustomizer.CompositeCategoryProvider.Registration(
			projectType = "org-netbeans-modules-apisupport-project",
			position = 1401)
})
public class PrjcfgAntJavaseCatProvider extends PrjcfgAntAbstractCatProvider implements ProjectCustomizer.CompositeCategoryProvider {
}
