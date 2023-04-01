package mim1qsdatagen.base.data.blockstate

import com.google.gson.JsonElement
import com.google.gson.JsonObject

/**
 * "Variant" type of Block State files, where each key corresponds to a model
 */
class VariantBlockState internal constructor() : BlockState() {
  override fun generate(): JsonElement {
    TODO("not implemented")
  }

  class Entry(
    private val key: String,
    firstModel: BlockStateModel,
    vararg otherModels: BlockStateModel
  ) : BlockStateEntry(firstModel, *otherModels) {
    override fun addToParent(parent: JsonObject) {
      parent.add(key, generateModelElement())
    }
  }
}