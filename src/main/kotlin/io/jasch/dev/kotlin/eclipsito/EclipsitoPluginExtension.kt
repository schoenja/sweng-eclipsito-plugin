package io.jasch.dev.kotlin.eclipsito

import org.gradle.api.Project

/**
 *
 * @author jasch
 * @version
 * @since
 */
open class EclipsitoPluginExtension() {
    //lateinit var project: Project
    var extPoints: LinkedHashMap<String, String> = LinkedHashMap<String, String>()
    var exts: LinkedHashMap<String, MutableList<String>> = LinkedHashMap<String, MutableList<String>>()


    fun extension(extension: LinkedHashMap<String, String>) {
        val extPoint = extension["point"]
        val extClassname = extension["classname"]
        if (extPoint != null && extClassname != null) {
            if (this.exts.containsKey(extPoint)) {
                this.exts[extPoint]!!.add(extClassname) // !! is safe here as we check whether the point exists.
                println("Added extension")
            } else {
                System.out.println("You need to define the extension point first!")
            }
        }
    }

    fun extensionpoint(extPoint: LinkedHashMap<String, String>) {
        val pointId = extPoint["id"]
        val pointName = extPoint["name"]
        if (pointId != null && pointName != null) {
            this.extPoints.put(pointId,pointName)
            this.exts.put(pointName, ArrayList<String>())
        }
    }
}