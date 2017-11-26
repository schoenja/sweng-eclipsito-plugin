package unibas.sweng.eclipsito.tasks

import unibas.sweng.eclipsito.EclipsitoPluginExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File


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

        plugin.append("\n    <library name=\"ganttproject.jar\">\n" +
                "      <export name=\"*\"/>\n" +
                "    </library>")

        project.configurations.getByName("runtime").forEach {
//            if (!myExtension.includeEclipsitoInXML && it.name.contains("eclipsito.jar")) {  }
            if (it.isFile && (myExtension.includeEclipsitoInXML || !it.name.contains("eclipsito.jar"))) {
//            if (it.isFile && !(!myExtension.includeEclipsitoInXML && it.name.contains("eclipsito.jar"))) {
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
                val extBuilder = StringBuilder("    ")
                extBuilder.append("<${extPoint.xmlTag} ")
                for (d in ext.data) {
                    extBuilder.append("${extPoint.data[d.key]!!.second}=\"${d.value}\" ")
                }
                extBuilder.append("/>")
                plugin.append(extBuilder.toString()).append("\n")
            }
//            for (ext in myExtension.exts.filter { it.point == extPoint.name }.listIterator()) {
//                plugin.append("    <${extPoint.xmlTag} class=\"${ext.classname}\" ${ext.additional ?: ""}/>\n")
//            }
            plugin.append("  </extension>\n")
        }



        plugin.append("</plugin>") // End of plugin tag
//        println(plugin.toString())

        myExtension.pluginXMLFile.writeText(plugin.toString())
    }
}