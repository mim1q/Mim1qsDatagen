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

  companion object {
    /**
     * Creates a simple, single-state Block State
     *
     * @param model the model to be used in the Block State
     * @return a new [VariantBlockState] instance with the specified model assigned to the `""` variant
     */
    fun createSingle(model: BlockStateModel): VariantBlockState {
      return create().variant("", model)
    }

    /**
     * Creates a simple, single-state Block State based on the specified model name
     *
     * @param modelName the name of the model to be used in the Block State
     * @return a new [VariantBlockState] instance with the specified model assigned to the `""` variant
     */
    fun createSingle(modelName: String): VariantBlockState {
      return createSingle(BlockStateModel(modelName))
    }

    /**
     * Creates a new Block State
     *
     * @return a new [VariantBlockState] instance with no specified variants
     */
    fun create(setup: VariantBlockState.() -> Unit = {}): VariantBlockState {
      return VariantBlockState().apply(setup)
    }

    /**
     * Creates a new Multipart Block State
     *
     * @return a new [MultipartBlockState] instance with no specified entries
     */
    fun createMultipart(setup: MultipartBlockState.() -> Unit = {}): MultipartBlockState {
      return MultipartBlockState().apply(setup)
    }
  }
}