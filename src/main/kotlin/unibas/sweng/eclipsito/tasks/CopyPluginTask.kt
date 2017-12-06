package unibas.sweng.eclipsito.tasks

import unibas.sweng.eclipsito.EclipsitoPluginExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * Copy plugin task
 *
 * This task replicates the behaviour of the `copyPlugin` task from
 * the original `ganttproject` build system.
 * @author jasch
 * @version 0.1.0
 * @since 0.1.0
 */
open class CopyPluginTask : DefaultTask() {

    @TaskAction
    fun copyPlugin() {
        val myExtension = project.extensions.getByName("eclipsito") as EclipsitoPluginExtension

        // Create target folder
        if (!File(myExtension.pluginsDirectory, "ganttproject").deleteRecursively()) {
            error("Failed to delete ganttproject folder. Aborting.")
            return
        }
        val targetFolder = File(myExtension.pluginsDirectory, "ganttproject")

        // Copy plugin.xml
        myExtension.pluginXMLFile.copyTo(File(targetFolder, "plugin.xml"))

        // Copy ganttproject jar
        myExtension.ganttprojectJarFile.copyTo(File(targetFolder, "ganttproject.jar"))

        // Copy dependencies
        val depTarget = File(targetFolder, "lib")

        project.configurations.getByName("runtime").forEach {
            if (it.isFile && (myExtension.includeEclipsitoInXML || !it.name.contains("eclipsito.jar"))) {
                it.absoluteFile.copyTo(File(depTarget, it.name), overwrite = true)
            }
        }

        // Copy resources
        val resTarget = File(targetFolder, "resources")
        myExtension.resourcesDirectory.copyRecursively(resTarget)

        // Copy eclipsito
        myExtension.eclipsitoJar.copyTo(File(myExtension.distBinDirectory, "eclipsito.jar"), overwrite = true)
    }
}