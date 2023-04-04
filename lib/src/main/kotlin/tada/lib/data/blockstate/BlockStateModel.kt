package tada.lib.data.blockstate

import com.google.gson.JsonElement
import com.google.gson.JsonObject

/**
 * Represents a single model in a block state entry
 *
 * @param model reference to the used model
 * @param xRot rotation of the model on the x-axis Default: NONE (0)
 * @param yRot rotation of the model on the y-axis. Default: NONE (0)
 * @param uvlock whether the block's texture rotation should be locked. Default: false
 * @param weight integer weight of the model. Only used when multiple models are present. Default: 1
 * @constructor returns the model with the given properties
 */
class BlockStateModel(
  private val model: String,
  private val xRot: Rotation = Rotation.NONE,
  private val yRot: Rotation = Rotation.NONE,
  private val uvlock: Boolean = false,
  private val weight: Int = 1
) {

  /**
   * Generates the representation of this model
   *
   * @param useWeight whether the result should contain the `weight` field
   * @return the representation of this model as a [JsonElement]
   */
  fun generate(useWeight: Boolean): JsonElement {
    return JsonObject().apply {
      addProperty("model", model)
      if (xRot != Rotation.NONE) addProperty("x", xRot.value)
      if (yRot != Rotation.NONE) addProperty("y", yRot.value)
      if (uvlock) addProperty("uvlock", true)
      if (useWeight) addProperty("weight", weight)
    }
  }

  /**
   * The rotation of a model entry in a block state file in 90-degree clockwise increments
   */
  enum class Rotation(val value: Int) {
    NONE(0),
    CW_90(90),
    CW_180(180),
    CW_270(270)
  }
}