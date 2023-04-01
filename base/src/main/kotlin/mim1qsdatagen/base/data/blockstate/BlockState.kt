package mim1qsdatagen.base.data.blockstate

import mim1qsdatagen.base.data.MinecraftData
import java.nio.file.Path

/**
 * Base class for a representation of BlockStates
 */
abstract class BlockState : MinecraftData {
  /**
   * Variants of the block with models associated to them
   */
  private val variants: MutableMap<String, String> = hashMapOf()

  /**
   * Adds a variant to this BlockState and returns the object
   *
   * @param key the key of the variant
   * @param model the model associated with this variant
   * @return the BlockState object with the new variant added
   */
  fun variant(key: String, model: String): BlockState {
    variants[key] = model
    return this
  }

  /**
   * Generates the text corresponding to a single variant
   *
   * @param variant the key of the variant
   * @param model the model associated with this variant
   * @return the generated string representation of the variant
   */
  protected abstract fun generateVariant(variant: String, model: String): String

  override fun generate(): String {
    return buildString {
      append("{\n")
      append(
        variants
          .toList()
          .joinToString(separator = ",\n") {
            generateVariant(it.first, it.second)
          }
      )
      append("}\n")
    }
  }

  override fun getDefaultOutputPath(baseDir: Path, namespace: String): Path {
    return baseDir.resolve("assets/${namespace}/blockstates")
  }
}