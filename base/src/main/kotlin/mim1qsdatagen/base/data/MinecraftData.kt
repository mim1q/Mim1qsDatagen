package mim1qsdatagen.base.data

import java.nio.file.Path

/**
 * Interface that represents a Minecraft asset or data file
 */
interface MinecraftData {
  /**
   * Generates the text that is going to be used in the created file
   *
   * @return the content of the file
   */
  fun generate(): String

  /**
   * @param baseDir the base directory of the generated project
   * @return the path of the directory in which the file should be generated
   */
  fun getDefaultOutputPath(baseDir: Path): Path
}