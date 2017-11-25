package io.jasch.dev.kotlin.eclipsito

import io.jasch.dev.kotlin.eclipsito.data.Extension
import io.jasch.dev.kotlin.eclipsito.data.ExtensionPoint
import org.gradle.api.Project

/**
 *
 * @author jasch
 * @version
 * @since
 */
open class EclipsitoPluginExtension() {
    var extPoints: ArrayList<ExtensionPoint> = ArrayList()
    var exts: ArrayList<Extension> = ArrayList()

    fun extension(extension: LinkedHashMap<String, String>) {
        val extPoint = extension["point"]
        val extClassname = extension["classname"]
        val extAdd = extension["additional"]
        if (extPoint != null && extClassname != null) {
            this.exts.add(Extension(extPoint, extClassname, extAdd))
        }
    }

    fun extensionpoint(extPoint: LinkedHashMap<String, String>) {
        val pointId = extPoint["id"]
        val pointName = extPoint["name"]
        val pointXml = extPoint["xml"]
//        val pointAdd = extPoint["additional"]
        if (pointId != null && pointName != null && pointXml != null) {
            this.extPoints.add(ExtensionPoint(pointId, pointName, pointXml))
        }
    }

    //lateinit var project: Project
    /*var extPoints: LinkedHashMap<String, String> = LinkedHashMap<String, String>()
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
    }*/
}