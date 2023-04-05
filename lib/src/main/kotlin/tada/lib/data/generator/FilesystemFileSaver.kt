package tada.lib.data.generator

import java.io.File
import java.nio.file.Path

/**
 * Implementation of [ResourceGenerator.FileSaver] that clears the base directory before generating and writes the
 * content to files saved on disk
 */
object FilesystemFileSaver : ResourceGenerator.FileSaver {
  /**
   * Clears the specified directory of all files and subdirectories
   *
   * @param directory the directory to clear
   */
  private fun clearDirectory(directory: File) {
    for (file in directory.listFiles()!!) {
      if (file.isDirectory) {
        clearDirectory(file)
      } else {
        file.delete()
      }
    }
  }

  /**
   * Prepares the base directory by clearing it
   */
  override fun prepare(baseDirectory: Path) {
    clearDirectory(baseDirectory.toFile())
  }

  /**
   * Saves the content to a file on disk
   */
  override fun save(filePath: Path, content: String) {
    filePath.toFile().writeText(content)
  }
}