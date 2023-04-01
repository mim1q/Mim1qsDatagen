package mim1qsdatagen.base.data.blockstate

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject

/**
 * "Multipart" type of Block State. Each model is applied under certain conditions, provided in the following string
 * format:
 *
 * List of state keys connected with corresponding values by an `=` sign, separated by commas. Spaces are ignored
 *
 * `"north=none, east=none"`
 *
 * Multiple values can be assigned to a single key by separating them with a `|`
 *
 * `"north=side|up, east=none"`
 *
 * @see <a href="https://minecraft.fandom.com/wiki/Tutorials/Models#Block_states">Minecraft Wiki for Block States</a
 */
class MultipartBlockState internal constructor() : BlockState() {
  private val entries = mutableListOf<Entry>()

  /**
   * Adds a new Multipart entry
   *
   * @param entry the entry to add
   * @return this [MultipartBlockState]
   */
  fun addEntry(entry: Entry): MultipartBlockState {
    entries.add(entry)
    return this
  }

  /**
   * Adds a new always-applied Multipart entry of the given model
   *
   * @param model the model to add as an always-applied multipart entry
   * @return this [MultipartBlockState]
   */
  fun apply(model: BlockStateModel): MultipartBlockState {
    addEntry(Entry(listOf(), Entry.ConditionType.ALWAYS, model))
    return this
  }

  /**
   * Adds a new Multipart entry of the given simple named model that is applied under a given condition
   * @param model the model of this entry
   * @param condition the condition of the entry
   * @return this [MultipartBlockState]
   */
  fun applyWhen(model: BlockStateModel, condition: String): MultipartBlockState {
    entries.add(Entry(listOf(condition), Entry.ConditionType.WHEN, model))
    return this
  }

  /**
   * Adds a new Multipart entry of the given model that is applied when all given conditions are satisfied
   *
   * @param model the model of this entry
   * @param conditions the conditions of the entry
   * @see [applyWhen] for more information on the condition syntax
   * @return this [MultipartBlockState]
   */
  fun applyWhenAll(model: BlockStateModel, vararg conditions: String): MultipartBlockState {
    entries.add(Entry(conditions.asList(), Entry.ConditionType.WHEN_ALL, model))
    return this
  }

  /**
   * Adds a new Multipart entry of the given model that is applied when any of the given conditions is satisfied
   *
   * @param model the model of this entry
   * @param conditions the conditions of the entry
   * @see [applyWhen] for more information on the condition syntax
   * @return this [MultipartBlockState]
   */
  fun applyWhenAny(model: BlockStateModel, vararg conditions: String): MultipartBlockState {
    entries.add(Entry(conditions.asList(), Entry.ConditionType.WHEN_ANY, model))
    return this
  }

  override fun generate(): JsonElement {
    return JsonObject().apply {
      add("multipart", JsonArray().apply {
        entries.forEach {entry ->
          add(JsonObject().apply { entry.addToParent(this) })
        }
      })
    }
  }

  /**
   * [MultipartBlockState]-specific implementation of [BlockStateEntry]
   * @param conditions the conditions as to when the model should be applied
   * @param conditionType type of the operation condition. See [ConditionType]
   * @param firstModel model to use for the entry
   * @param otherModels (optional) additional models
   */
  class Entry(
    private val conditions: List<String>,
    private val conditionType: ConditionType,
    firstModel: BlockStateModel,
    vararg otherModels: BlockStateModel
  ) : BlockStateEntry(firstModel, *otherModels) {
    override fun addToParent(parent: JsonObject) {
      when (conditionType) {
        ConditionType.ALWAYS -> {}
        ConditionType.WHEN -> {
          if (conditions.isEmpty()) throw IllegalStateException("No conditions have been provided")
          parent.add("when", generateCondition(conditions[0]))
        }
        ConditionType.WHEN_ALL, ConditionType.WHEN_ANY -> {
          if (conditions.size <= 1) throw IllegalStateException("Less than two conditions have been provided")
          parent.add("when", JsonObject().apply {
            add(conditionType.key, JsonArray().apply { conditions.forEach { add(generateCondition(it)) } })
          })
        }
      }
      parent.add("apply", generateModelElement())
    }

    /**
     * Generates a condition object from the given string
     * @param condition the condition represented as a string
     * @return the [JsonObject] of the condition
     */
    private fun generateCondition(condition: String): JsonObject {
      val result = JsonObject()
      try {
        condition.replace(" ", "").split(",").forEach { outerIt ->
          outerIt.split("=").let {
            result.addProperty(it[0], it[1])
          }
        }
      } catch (e: IndexOutOfBoundsException) {
        throw IllegalStateException("Invalid condition: $condition")
      }
      return result
    }

    /**
     * Types of Multipart condition types
     */
    enum class ConditionType(val key: String?) {
      /**
       * Always applied
       */
      ALWAYS(null),
      /**
       * Applied when the given condition is satisfied
       */
      WHEN(null),
      /**
       * Applied when any of the given conditions is satisfied
       */
      WHEN_ANY("OR"),
      /**
       * Applied when all given conditions are satisfied
       */
      WHEN_ALL("AND")
    }
  }
}