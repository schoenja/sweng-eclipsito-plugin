package io.jasch.dev.kotlin.eclipsito

import io.jasch.dev.kotlin.eclipsito.tasks.CopyPluginTask
import io.jasch.dev.kotlin.eclipsito.tasks.CreatePluginXMLTask
import io.jasch.dev.kotlin.eclipsito.tasks.ListExtensionsTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ApplicationPlugin

/**
 *
 * @author jasch
 * @version 0.0.1
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