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
open class CopyEclipsitoTask : DefaultTask() {

    @TaskAction
    fun copyEclipsito() {
        val myExtension = project.extensions.getByName("eclipsito") as EclipsitoPluginExtension


    }
}