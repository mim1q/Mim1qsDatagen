package tada.lib.resources.blockstate

import com.google.gson.JsonObject

/**
 * "Variant" type of Block State files, where each key corresponds to a model. Variants are provided in the following
 * string format:
 *
 * List of state keys connected with corresponding values by an `=` sign, separated by commas. Spaces are ignored
 * "facing=east, lit=false"
 *
 * @see <a href="https://minecraft.fandom.com/wiki/Tutorials/Models#Block_states">Minecraft Wiki for Block States</a
 */
class VariantBlockState internal constructor() : BlockState() {
  /**
   * List of all the variants of this Block State
   */
  private val variants = mutableListOf<Entry>()

  /**
   * Adds a variant entry with the given key and models
   *
   * @param key the key of the variant entry
   * @param firstModel the main model to add to the variant entry
   * @param otherModels (optional) additional models
   * @return this [VariantBlockState]
   */
  fun variant(
    key: String,
    firstModel: BlockStateModel,
    vararg otherModels: BlockStateModel
  ): VariantBlockState {
    variants.add(Entry(key, firstModel, *otherModels))
    return this
  }

  /**
   * Adds a variant entry with the given key and a simple named model
   *
   * @param key the key of the variant entry
   * @param modelName reference to the model of this variant entry
   * @return this [VariantBlockState]
   */
  fun variant(key: String, modelName: String): VariantBlockState {
    variants.add(Entry(key, BlockStateModel(modelName)))
    return this
  }

  /**
   * @throws IllegalArgumentException if no variants have been provided
   */
  override fun generate(): JsonObject {
    if (variants.isEmpty()) throw IllegalStateException("No variants have been provided")
    return JsonObject().apply {
      add("variants", JsonObject().apply {
        variants.forEach {
          it.addToParent(this)
        }
      })
    }
  }

  /**
   * [VariantBlockState]-specific implementation of [BlockStateEntry]
   * @param key the key of the variant entry (spaces are removed)
   * @param firstModel model to use for the entry
   * @param otherModels (optional) additional models
   */
  class Entry(
    private val key: String,
    firstModel: BlockStateModel,
    vararg otherModels: BlockStateModel
  ) : BlockStateEntry(firstModel, *otherModels) {
    override fun addToParent(parent: JsonObject) {
      parent.add(key.replace(" ", ""), generateModelElement())
    }
  }
}