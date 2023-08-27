package tada.lib.resources

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.nio.file.Path

/**
 * Interface that represents a Minecraft asset or data file
 */
abstract class MinecraftResource {
  private var postProcessor: JsonObject.() -> Unit = {}

  /**
   * Generates the content that is going to be used in the created file
   *
   * @return the generated JSON representation of the data
   */
  internal abstract fun generate(): JsonObject

  /**
   * Returns the output path of the given data type
   *
   * @param baseDir the base directory of the generated project
   * @param namespace the namespace of the project
   * @return the path of the directory in which the file should be generated
   */
  abstract fun getDefaultOutputDirectory(baseDir: Path, namespace: String): Path

  /**
   * Generate the json representation and post-process it using the specified post-processor
   */
  fun postProcessAndGenerate(): JsonObject {
    val json = generate()
    json.postProcessor()
    return json
  }

  /**
   * Set the post-processor for the generated json
   */
  fun postProcess(postProcessor: JsonObject.() -> Unit) = this.apply { this.postProcessor = postProcessor }
}