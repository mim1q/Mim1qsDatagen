package mim1qsdatagen.base.data.blockstate

import mim1qsdatagen.base.data.MinecraftData
import java.nio.file.Path

/**
 * Base class for Block State data
 */
abstract class BlockState : MinecraftData {
  override fun getDefaultOutputPath(baseDir: Path, namespace: String): Path {
    return baseDir.resolve("assets/${namespace}/blockstates/")
  }
}