package tada.lib.resources.recipe

import com.google.gson.JsonObject
import tada.lib.util.Id

/**
 * Recipe for stonecutting
 */
class StonecuttingRecipe private constructor(
  private val ingredient: Id,
  private val result: Id,
  private val count: Int
) : Recipe(Id("stonecutting")) {
  override fun addData(element: JsonObject) {
    element.add("ingredient", JsonObject().apply {
      addProperty(if (ingredient.isTag) "tag" else "item", ingredient.toString())
    })
    element.addProperty("result", result.toString())
    element.addProperty("count", count)
  }

  companion object {
    /**
     * Creates a new stonecutter recipe
     * @param ingredient The ingredient
     * @param result The resulting item
     * @param count The resulting item's count
     *
     * @return The new stonecutting recipe
     */
    fun create(ingredient: String, result: String, count: Int = 1): StonecuttingRecipe {
      return StonecuttingRecipe(Id(ingredient), Id(result), count)
    }
  }
}