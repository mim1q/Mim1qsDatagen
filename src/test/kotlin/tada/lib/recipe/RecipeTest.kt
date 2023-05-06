package tada.lib.recipe

import org.junit.jupiter.api.Test
import tada.lib.TestUtil
import tada.lib.resources.recipe.CraftingRecipe
import tada.lib.resources.recipe.SmeltingRecipe
import tada.lib.resources.recipe.StonecuttingRecipe

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
          "ingredients": [ {"item": "test:item"}, {"tag": "test:test_tag"}],
          "result": {"item": "minecraft:test_item", "count": 5}
        }
      """,
      CraftingRecipe.shapeless("test_item", 5) {
        ingredient("test:item")
        ingredient("#test:test_tag")
      }.generate()
    )
  }

  @Test
  fun `smelting recipe`() {
    TestUtil.assertJsonEquals(
      """
        {
          "type": "minecraft:smelting",
          "ingredient": {"item": "test:test_input"},
          "result": "test:test_output"
        }
      """,
      SmeltingRecipe.create("test:test_input", "test:test_output").generate()
    )
  }

  @Test
  fun `blasting recipe`() {
    TestUtil.assertJsonEquals(
      """
      {
        "type": "minecraft:blasting",
        "ingredient": {"item": "test:test_input"},
        "result": "test:test_output",
        "experience": 0.5,
        "cookingtime": 100
      }
      """,
      SmeltingRecipe.blasting("test:test_input", "test:test_output", 0.5, 100).generate()
    )
  }

  @Test
  fun `smoking recipe`() {
    TestUtil.assertJsonEquals(
    """
      {
        "type": "minecraft:smoking",
        "ingredient": {"tag": "test:test_tag"},
        "result": "test:test_output",
        "cookingtime": 120
      }
      """,
      SmeltingRecipe.smoking("#test:test_tag", "test:test_output", null, 120).generate()
    )
  }

  @Test
  fun `campfire cooking recipe`() {
    TestUtil.assertJsonEquals(
      """
      {
        "type": "minecraft:campfire_cooking",
        "ingredient": {"item": "test:test_input"},
        "result": "test:test_output",
        "experience": 5.25
      }
      """,
      SmeltingRecipe.campfire("test:test_input", "test:test_output", 5.25).generate()
    )
  }

  @Test
  fun `stonecutting recipe`() {
    TestUtil.assertJsonEquals(
      """
      {
        "type": "minecraft:stonecutting",
        "ingredient": {
          "tag": "test:test_input"
        },
        "result": "test:test_output",
        "count": 16
      }
      """,
      StonecuttingRecipe.create("#test:test_input", "test:test_output", 16).generate()
    )
  }
}