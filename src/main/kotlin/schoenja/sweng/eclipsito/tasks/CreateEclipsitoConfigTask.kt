package schoenja.sweng.eclipsito.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import schoenja.sweng.eclipsito.EclipsitoPluginExtension

/**
 *
 * @author jasch
 * @version 0.2.0
 * @since 0.1.2
 */
open class CreateEclipsitoConfigTask : DefaultTask() {

    @TaskAction
    fun createEclipsitoConfig() {
        val myExtension = project.extensions.getByName("eclipsito") as EclipsitoPluginExtension

        val eclipsitoConfigFile = myExtension.eclipsitoConfigFile
        val pluginDirString = myExtension.pluginsDirectory.toRelativeString(myExtension.distBinDirectory)
        println("$pluginDirString")

        val newConfig = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<configuration name=\"ganttproject\"\n" +
                "  platform-classname=\"org.bardsoftware.impl.eclipsito.BootImpl\"\n" +
                "  modules-resource=\"/$pluginDirString\"\n" +
                "  descriptor-file-pattern=\"plugin.xml\"\n" +
                "  logging-level=\"ALL\"\n" +
                "  application=\"biz.ganttproject.GanttProject\"\n" +
                "/>\n"

        eclipsitoConfigFile.writeText(newConfig)
    }
}