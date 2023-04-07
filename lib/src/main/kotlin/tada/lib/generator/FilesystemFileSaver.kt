package tada.lib.generator

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createFile
import kotlin.io.path.exists

/**
 * Implementation of [ResourceGenerator.FileSaver] that writes the content to files saved on disk
 */
object FilesystemFileSaver : ResourceGenerator.FileSaver {
  override fun prepare(baseDirectory: Path) { }

  /**
   * Saves the content to a file on disk
   */
  override fun save(filePath: Path, content: String) {
    Files.createDirectories(filePath.parent)
    if (!filePath.exists()) {
      filePath.createFile()
    }
    filePath.toFile().writeText(content)
  }
}