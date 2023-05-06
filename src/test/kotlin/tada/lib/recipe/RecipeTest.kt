package tada.lib.recipe

import org.junit.jupiter.api.Test
import tada.lib.TestUtil
import tada.lib.resources.recipe.CraftingRecipe

internal class RecipeTest {
  @Test
  fun `simple shaped recipe`() {
    TestUtil.assertJsonEquals(
      """
      {
        "type": "minecraft:crafting_shaped",
        "pattern": [
          "AAA",
          " B ",
          " C "
        ],
        "key": {
          "A": {"item": "minecraft:item_a"},
          "B": {"item": "minecraft:item_b"},
          "C": {"tag": "minecraft:tag_c"}
        },
        "result": {"item": "minecraft:test_item", "count": 20}
      }
      """,
      CraftingRecipe.shaped("test_item", 20) {
        pattern("AAA", " B ", " C ")
        key("A", "item_a")
        key("B", "item_b")
        key("C", "#tag_c")
      }.generate()
    )
  }

  @Test
  fun `simple shapeless recipe`() {
    TestUtil.assertJsonEquals(
      """
        {
          "type": "minecraft:crafting_shapeless",
          "ingredients": [
            {
              "item": "test:item"
            },
            {
              "tag": "test:test_tag"
            }
          ],
          "result": {
            "item": "minecraft:test_item",
            "count": 5
          }
        }
      """,
      CraftingRecipe.shapeless("test_item", 5) {
        ingredient("test:item")
        ingredient("#test:test_tag")
      }.generate()
    )
  }
}