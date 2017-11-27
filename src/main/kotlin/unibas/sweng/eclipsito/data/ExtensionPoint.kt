package unibas.sweng.eclipsito.data

/**
 * Stores the information about an extension point.
 *
 * @author jasch
 * @version 0.1.0
 * @since 0.1.0
 * @param id The ID of the extension point
 * @param name The name used by extensions for this extension point
 * @param xmlTag The name of the XML tag used by extensions for this extension point
 * @param data Attributes of the extensions. Key is name, value is xml attribute name
 */
data class ExtensionPoint(
        val id: String,
        val name: String,
        val xmlTag: String,
        val data: HashMap<String, Pair<Boolean, String>>
)