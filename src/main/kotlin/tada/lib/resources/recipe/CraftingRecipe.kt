package tada.lib.resources.recipe

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import tada.lib.util.Id

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

    fun pattern(vararg pattern: String) {
      if (pattern.size > 3 || pattern.map { it.length }.toSet().size != 1) {
        val message = pattern.joinToString("; ")
        throw IllegalArgumentException("Invalid size of pattern: $message")
      }
      patterns.addAll(pattern)
    }

    fun key(key: String, value: String) {
      keys[key] = Id(value)
    }
  }

  class Shapeless internal constructor(result: Id, count: Int)
    : CraftingRecipe(Id("crafting_shapeless"), result, count) {
    private val ingredients: ArrayList<Id> = arrayListOf()

    override fun addData(element: JsonObject) {
      element.add("ingredients", JsonArray().apply {
        ingredients.forEach {
          add(JsonObject().apply {
            addProperty(if (it.isTag) "tag" else "item", it.toString())
          })
        }
      })
      element.add("result", generateResult())
    }

    fun ingredient(id: String) {
      ingredients.add(Id(id))
    }
  }

  companion object {
    fun shaped(result: String, count: Int = 1, setup: Shaped.() -> Unit): Shaped = Shaped(Id(result), count).apply(setup)
    fun shapeless(result: String, count: Int = 1, setup: Shapeless.() -> Unit): Shapeless = Shapeless(Id(result), count).apply(setup)
  }
}