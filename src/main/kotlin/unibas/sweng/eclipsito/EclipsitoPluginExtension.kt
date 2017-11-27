package unibas.sweng.eclipsito

import groovyjarjarcommonscli.MissingArgumentException
import unibas.sweng.eclipsito.data.Extension
import unibas.sweng.eclipsito.data.ExtensionPoint
import org.gradle.api.Project
import java.io.File

/**
 * Extension object for the eclipsito plugin
 * @author jasch
 * @version 0.1.0
 * @since 0.1.0
 */
open class EclipsitoPluginExtension() {
    var extPoints: ArrayList<ExtensionPoint> = ArrayList()
    var exts: ArrayList<Extension> = ArrayList()

    var includeEclipsitoInXML = false

    lateinit var pluginXMLFile: File
    lateinit var distBinDirectory: File
    lateinit var pluginsDirectory: File
    lateinit var ganttprojectJarFile: File
    lateinit var eclipsitoJar: File
    lateinit var resourcesDirectory: File

    fun pluginxml(file: File) {
        this.pluginXMLFile = file
    }

    fun extension(extension: LinkedHashMap<String, String>) {
        val extPoint = extension["point"]
        val usedExtPoint = this.extPoints.firstOrNull { it.name == extPoint }
        if (usedExtPoint != null) {
            val data = HashMap<String, String?>()
            val dataPoints = usedExtPoint.data
            for (point in dataPoints) {
                if (extension.containsKey(point.key)) {
                    data[point.key] = extension[point.key]
                } else {
                    if (point.value.first) throw MissingArgumentException("Missing key: ${point.key}.")
                }
            }

            this.exts.add(Extension(usedExtPoint.name, data))
        }
    }

    fun extensionpoint(extPoint: LinkedHashMap<String, Object>) {
        val pointId = extPoint["id"] as String
        val pointName = extPoint["name"] as String
        val pointXml = extPoint["xml"] as String
        val pointData = extPoint["data"] as HashMap<String, String>
        val data = HashMap<String, Pair<Boolean, String>>()
        for (d in pointData) {
            if (d.key.startsWith("_")) {
                data[d.key.drop(1)] = Pair(false, d.value)
            } else {
                data[d.key] = Pair(true, d.value)
            }
        }
        this.extPoints.add(ExtensionPoint(pointId, pointName, pointXml, data))
    }
}