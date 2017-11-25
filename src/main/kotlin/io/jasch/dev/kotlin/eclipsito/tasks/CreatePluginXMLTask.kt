package io.jasch.dev.kotlin.eclipsito.tasks

import io.jasch.dev.kotlin.eclipsito.EclipsitoPluginExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction


/**
 *
 * @author jasch
 * @version
 * @since
 */
open class CreatePluginXMLTask : DefaultTask() {

    @TaskAction
    fun createPluginXML() {
        val myExtension = project.extensions.getByName("eclipsito") as EclipsitoPluginExtension

//        val myExtPoints = myExtension.extPoints

        val plugin = StringBuilder() // Ghetto XML creation, because other stuff is too heavy

        plugin.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<plugin\n" +
                "   id=\"biz.ganttproject\"\n" +
                "   name=\"Ganttproject Plug-in\"\n" +
                "   version=\"2.0.0\"\n" +
                "   provider-name=\"\">") // Begin of plugin tag

        for (extPoint in myExtension.extPoints) {
            plugin.append("\n")
            plugin.append("  <extension-point id=\"${extPoint.key}\" name=\"${extPoint.value}\"/>")
        }

        plugin.append("  <extension point=\"org.eclipse.core.runtime.applications\" id=\"GanttProject\">\n" +
                "    <application>\n" +
                "      <run class=\"biz.ganttproject.application.MainApplication\"/>\n" +
                "    </application>\n" +
                "  </extension>") // Entry point extension

        for (ext in myExtension.exts) {
            plugin.append("  <extension point=\"biz.ganttproject.${ext.key}\">\n")
            for (entry in ext.value) {
                plugin.append("")
            }
        }


        plugin.append("\n</plugin>") // End of plugin tag
        println(plugin.toString())

        /*val plugin = xml("pluging") {
            attribute("id", "biz.ganttproject")
            attribute("name", "Ganttproject plug-in")
            attribute("version", "2.0.0")
            attribute("provider-name", "")

            for (extPoint in myExtPoints) {
                "extension-point" {
                    attribute("id", extPoint.key)
                    attribute("name", extPoint.value)
                }
            }
        }

        println(plugin.toString())*/
    }
}