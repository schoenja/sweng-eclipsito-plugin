package schoenja.sweng.eclipsito.tasks

import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.bundling.Jar
import schoenja.sweng.eclipsito.EclipsitoPluginExtension

/**
 *
 * @author jasch
 * @version
 * @since
 */
open class UpdateEclipsitoJarTask : Jar() {
    @TaskAction
    fun updateEclipsitoJar() {
        val myExtension = project.extensions.getByName("eclipsito") as EclipsitoPluginExtension

        from(myExtension.eclipsitoJarCollection)
        from(myExtension.eclipsitoConfigFile)
        baseName = "eclipsito.jar"
        destinationDir = myExtension.eclipsitoJar.parentFile
    }
}