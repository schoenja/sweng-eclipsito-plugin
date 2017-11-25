package io.jasch.dev.kotlin.eclipsito.tasks

import io.jasch.dev.kotlin.eclipsito.EclipsitoPluginExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.impldep.org.bouncycastle.asn1.x500.style.RFC4519Style.description

/**
 *
 * @author jasch
 * @version
 * @since
 */
open class ListExtensionsTask : DefaultTask() {

    @TaskAction
    fun listExtensions() {
//        println(project.extensions.getByName("eclipsito").toString())

        val myExtension = project.extensions.getByName("eclipsito") as EclipsitoPluginExtension
//        println(myExtension.toString())
//        println(myExtension.exts)
        val myExtensions = myExtension.exts
//        println(myExporterClasses)
        for (extPoint in myExtensions) {
            println("Extension point: ${extPoint.point}:")
//            for (ex in extPoint.) {
//                println("  $ex")
//            }
        }
    }
}