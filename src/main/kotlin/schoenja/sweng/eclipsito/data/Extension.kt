package schoenja.sweng.eclipsito.data

/**
 *
 * @author jasch
 * @version 0.1.0
 * @since 0.1.0
 * @param point The name of the extension point used by this extension
 * @param data The parameters for this extension
 */
data class Extension(
     val point: String,
     val data: HashMap<String, String?>
)