package tada.lib.resources.blockstate

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject

/**
 * A class representing one entry in a Block State file
 * @param firstModel model to use for the entry
 * @param otherModels (optional) additional models
 */
abstract class BlockStateEntry(
  firstModel: BlockStateModel,
  vararg otherModels: BlockStateModel
) {
  private val models = mutableListOf<BlockStateModel>()
  init {
    models.add(firstModel)
    models.addAll(otherModels)
  }

  /**
   * Creates an array of provided models with their weights
   * @return [JsonArray] of models added to this entry
   */
  private fun generateWeightedModelArray(): JsonArray {
    val result = JsonArray()
    models.forEach {
      result.add(it.generate(true))
    }
    return result
  }

  /**
   * Generates the element that represents the model associated with this entry
   * @return a [JsonElement] of the provided model if there is only one, otherwise a [JsonArray] containing the provided
   * models with their corresponding weights
   * @throws IllegalArgumentException if no model is provided
   */
  fun generateModelElement(): JsonElement {
    if (models.size == 0) throw IllegalStateException("No models provided")
    if (models.size == 1) return models[0].generate(false)
    return generateWeightedModelArray()
  }

  /**
   * Adds the JSON representation of this entry to the given parent object
   * @param parent the parent [JsonObject]
   */
  abstract fun addToParent(parent: JsonObject)
}