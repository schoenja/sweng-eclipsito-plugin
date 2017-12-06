package unibas.sweng.eclipsito

import org.gradle.api.Plugin
import org.gradle.api.Project
import unibas.sweng.eclipsito.tasks.CopyPluginTask
import unibas.sweng.eclipsito.tasks.CreateEclipsitoConfigTask
import unibas.sweng.eclipsito.tasks.CreatePluginXMLTask
import unibas.sweng.eclipsito.tasks.UpdateEclipsitoJarTask

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
        val createPluginXML = project.tasks.create("createPluginXML", CreatePluginXMLTask::class.java)
        val copyPlugin = project.tasks.create("copyPlugin", CopyPluginTask::class.java)
        val createEclipsitoConfig = project.tasks.create("createEclipsitoConfig", CreateEclipsitoConfigTask::class.java)
        val updateEclipsitoJar = project.tasks.create("updateEclipsitoJar", UpdateEclipsitoJarTask::class.java)
        updateEclipsitoJar.dependsOn(createEclipsitoConfig)

        copyPlugin.dependsOn(createPluginXML)
    }
}