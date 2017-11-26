package unibas.sweng.eclipsito

import org.gradle.api.Plugin
import org.gradle.api.Project
import unibas.sweng.eclipsito.tasks.CopyPluginTask
import unibas.sweng.eclipsito.tasks.CreatePluginXMLTask
import unibas.sweng.eclipsito.tasks.ListExtensionsTask

/**
 * The eclipsito plugin for ganttproject
 *
 * This plugin is made as an interface between the gradle build system
 * and the eclipsito framework for ganttproject. Its main purpose is
 * to hide the eclipsito overhead from the user.
 *
 * @author jasch
 * @version 0.0.16
 * @since 0.0.1
 */
class EclipsitoPlugin : Plugin<Project> {

    companion object {
        const val PLUGIN_EXTENSION_NAME = "eclipsito"
    }

    lateinit var project: Project

    override fun apply(project: Project) {
        this.project = project
        project.extensions.create(PLUGIN_EXTENSION_NAME, EclipsitoPluginExtension::class.java)
        val listExtensionPointsTask = project.tasks.create("listExtensionPoints", ListExtensionsTask::class.java)
        val createPluginXML = project.tasks.create("createPluginXML", CreatePluginXMLTask::class.java)
        val copyPlugin = project.tasks.create("copyPlugin", CopyPluginTask::class.java)

        copyPlugin.dependsOn(createPluginXML)
//        testprintPluginXMLTask.dependsOn(listExtensionPointsTask)
//        if (project.plugins?.withType(ApplicationPlugin::class.java)?.isNotEmpty()!!) {
//            project.extensions.create(PLUGIN_EXTENSION_NAME, EclipsitoPluginExtension::class.java)
//        }
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}