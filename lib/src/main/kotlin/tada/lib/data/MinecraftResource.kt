package tada.lib.data

import com.google.gson.JsonElement
import java.nio.file.Path

/**
 * Interface that represents a Minecraft asset or data file
 */
interface MinecraftResource {
  /**
   * Generates the content that is going to be used in the created file
   *
   * @return the generated JSON representation of the data
   */
  fun generate(): JsonElement

  /**
   * Returns the output path of the given data type
   *
   * @param baseDir the base directory of the generated project
   * @param namespace the namespace of the project
   * @return the path of the directory in which the file should be generated
   */
  fun getDefaultOutputDirectory(baseDir: Path, namespace: String): Path
}