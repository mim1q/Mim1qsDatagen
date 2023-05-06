package tada.lib.resources.recipe

import com.google.gson.JsonObject
import tada.lib.util.Id

/**
 * Represents a smelting recipe, used in furnaces, blast furnaces, etc.
 */
class SmeltingRecipe private constructor(
  type: Id,
  private val ingredient: Id,
  private val result: Id,
  private val experience: Double?,
  private val cookingTime: Int?
) : Recipe(type) {
  override fun addData(element: JsonObject) {
    element.add("ingredient", JsonObject().apply {
      addProperty(if (ingredient.isTag) "tag" else "item", ingredient.toString())
    })
    element.addProperty("result", result.toString())
    if (experience != null) element.addProperty("experience", experience)
    if (cookingTime != null) element.addProperty("cookingtime", cookingTime)
  }

  companion object {
    /**
     * Creates a smelting recipe used in furnaces.
     * @param ingredient The item/tag to smelt
     * @param result The resulting item
     * @param experience The experience gained
     * @param cookingTime The cook time in ticks
     *
     * @return The new smelting recipe
     */
    fun create(ingredient: String, result: String, experience: Double?  = null, cookingTime: Int? = null): SmeltingRecipe {
      return SmeltingRecipe(Id("smelting"), Id(ingredient), Id(result), experience, cookingTime)
    }

    /**
     * Creates a blasting recipe used in blast furnaces.
     * @param ingredient The item/tag to smelt
     * @param result The resulting item
     * @param experience The experience gained
     * @param cookingTime The cook time in ticks
     *
     * @return The new blasting recipe
     */
    fun blasting(ingredient: String, result: String, experience: Double?  = null, cookingTime: Int? = null): SmeltingRecipe {
      return SmeltingRecipe(Id("blasting"), Id(ingredient), Id(result), experience, cookingTime)
    }

    /**
     * Creates a smoking recipe used in smokers.
     * @param ingredient The item/tag to smelt
     * @param result The resulting item
     * @param experience The experience gained
     * @param cookingTime The cook time in ticks
     *
     * @return The new smoking recipe
     */
    fun smoking(ingredient: String, result: String, experience: Double?  = null, cookingTime: Int? = null): SmeltingRecipe {
      return SmeltingRecipe(Id("smoking"), Id(ingredient), Id(result), experience, cookingTime)
    }

    /**
     * Creates a campfire cooking recipe used in campfires.
     * @param ingredient The item/tag to smelt
     * @param result The resulting item
     * @param experience The experience gained
     * @param cookingTime The cook time in ticks
     *
     * @return The new campfire cooking recipe
     */
    fun campfire(ingredient: String, result: String, experience: Double?  = null, cookingTime: Int? = null): SmeltingRecipe {
      return SmeltingRecipe(Id("campfire_cooking"), Id(ingredient), Id(result), experience, cookingTime)
    }
  }
}