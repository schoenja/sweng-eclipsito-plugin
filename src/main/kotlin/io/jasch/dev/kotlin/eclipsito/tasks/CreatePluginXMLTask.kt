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

        println(myExtension.exts)

        val plugin = StringBuilder() // Ghetto XML creation, because other stuff is too heavy

        plugin.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<plugin\n" +
                "   id=\"biz.ganttproject\"\n" +
                "   name=\"Ganttproject Plug-in\"\n" +
                "   version=\"2.0.0\"\n" +
                "   provider-name=\"\">") // Begin of plugin tag

        // Let the dependency hell begin!
        plugin.append("\n\n  <runtime>")

        project.configurations.getByName("runtime").forEach {
            if (it.isFile) {
                plugin.append("\n    <library name=\"lib/${it.name}\">\n" +
                        "      <export name=\"*\"/>\n" +
                        "    </library>")
            }
        }

        plugin.append("\n  </runtime>\n")

        myExtension.extPoints.forEach {
            plugin.append("\n")
            plugin.append("  <extension-point id=\"${it.id}\" name=\"${it.name}\"/>")
        }

        plugin.append("\n  <extension point=\"org.eclipse.core.runtime.applications\" id=\"GanttProject\">\n" +
                "    <application>\n" +
                "      <run class=\"biz.ganttproject.application.MainApplication\"/>\n" +
                "    </application>\n" +
                "  </extension>") // Entry point extension

        plugin.append("\n")
        for (extPoint in myExtension.extPoints.listIterator()) {
            plugin.append("\n  <extension point=\"biz.ganttproject.${extPoint.id}\">\n")
            for (ext in myExtension.exts.filter { it.point == extPoint.name }.listIterator()) {
                plugin.append("    <${extPoint.xmlTag} class=\"${ext.classname}\" ${ext.additional ?: ""}/>\n")
            }
            plugin.append("  </extension>\n")
        }



        plugin.append("</plugin>") // End of plugin tag
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