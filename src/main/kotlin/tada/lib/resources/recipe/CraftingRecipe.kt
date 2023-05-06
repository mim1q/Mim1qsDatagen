package tada.lib.resources.recipe

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import tada.lib.util.Id

/**
 * Recipe for crafting items using a crafting grid.
 */
sealed class CraftingRecipe private constructor(
  type: Id,
  private val result: Id,
  private val count: Int
) : Recipe (type) {
  protected fun generateResult(): JsonObject {
    return JsonObject().apply {
      addProperty("item", result.toString())
      addProperty("count", count)
    }
  }

  /**
   * Represents a shaped crafting recipe. The pattern and ingredient keys are required.
   */
  class Shaped internal constructor(result: Id, count: Int)
    : CraftingRecipe(Id("crafting_shaped"), result, count) {
    private val patterns: ArrayList<String> = arrayListOf()
    private val keys: MutableMap<String, Id> = mutableMapOf()

    override fun addData(element: JsonObject) {
      if (patterns.isEmpty() || keys.isEmpty()) {
        throw IllegalStateException("No pattern or key provided")
      }
      element.add("pattern", JsonArray().apply {
        patterns.forEach { add(it)}
      })
      element.add("key", JsonObject().apply {
        keys.forEach {
          add(it.key, JsonObject().apply {
            addProperty(if (it.value.isTag) "tag" else "item", it.value.toString())
          })
        }
      })
      element.add("result", generateResult())
    }

    /**
     * @param pattern the pattern of the recipe. The length of the pattern must be the same for all rows.
     * (max length: 3, max rows: 3)
     */
    fun pattern(vararg pattern: String) {
      if (pattern.size > 3 || pattern.map { it.length }.toSet().size != 1) {
        val message = pattern.joinToString("; ")
        throw IllegalArgumentException("Invalid size of pattern: $message")
      }
      patterns.addAll(pattern)
    }

    /**
     * @param key the key of an ingredient
     * @param value the id of the ingredient
     */
    fun key(key: String, value: String) {
      keys[key] = Id(value)
    }
  }

  /**
   * Represents a shapeless crafting recipe. The ingredients are required.
   */
  class Shapeless internal constructor(result: Id, count: Int)
    : CraftingRecipe(Id("crafting_shapeless"), result, count) {
    private val ingredients: ArrayList<Id> = arrayListOf()

    override fun addData(element: JsonObject) {
      if (ingredients.isEmpty()) {
        throw IllegalStateException("No ingredients provided")
      }
      element.add("ingredients", JsonArray().apply {
        ingredients.forEach {
          add(JsonObject().apply {
            addProperty(if (it.isTag) "tag" else "item", it.toString())
          })
        }
      })
      element.add("result", generateResult())
    }

    /**
     * Add an ingredient to the recipe.
     *
     * @param id the id of the ingredient
     */
    fun ingredient(id: String) {
      ingredients.add(Id(id))
    }
  }

  companion object {
    /**
     * @param result the id of the resulting item
     * @param count the amount of items to craft
     * @param setup the setup function for the recipe
     *
     * @return a new shaped crafting recipe
     */
    fun shaped(result: String, count: Int = 1, setup: Shaped.() -> Unit): Shaped = Shaped(Id(result), count).apply(setup)

    /**
     * @param result the id of the resulting item
     * @param count the amount of items to craft
     * @param setup the setup function for the recipe
     *
     * @return a new shapeless crafting recipe
     */
    fun shapeless(result: String, count: Int = 1, setup: Shapeless.() -> Unit): Shapeless = Shapeless(Id(result), count).apply(setup)
  }
}