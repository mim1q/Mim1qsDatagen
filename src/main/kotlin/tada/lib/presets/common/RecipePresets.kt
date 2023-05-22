package tada.lib.presets.common

import tada.lib.presets.Preset
import tada.lib.resources.recipe.CraftingRecipe
import tada.lib.util.Id

object CommonRecipePresets {
  fun oneToOne(input: String, output: String, count: Int = 1) = Preset {
    val (_, inputName) = Id(input)
    val (_, outputName) = Id(output)
    add("${inputName}_to_$outputName", CraftingRecipe.shapeless(output, count) { ingredient(input) })
  }

  fun slab(input: String, output: String = "${input}_slab", count: Int = 6) = Preset {
    val (_, outputName) = Id(output)
    add(outputName, CraftingRecipe.shaped(output, count) {
      pattern("XXX")
      key("X", input)
    })
  }

  fun stairs(input: String, output: String = "${input}_stairs", count: Int = 4) = Preset {
    val (_, outputName) = Id(output)
    add(outputName, CraftingRecipe.shaped(output, count) {
      pattern(
        "X  ",
        "XX ",
        "XXX"
      )
      key("X", input)
    })
    add("${outputName}_flipped", CraftingRecipe.shaped(output, count) {
      pattern(
        "  X",
        " XX",
        "XXX"
      )
      key("X", input)
    })
  }

  fun packed2x2(input: String, output: String, count: Int = 1) = Preset {
    val (_, outputName) = Id(output)
    val (_, inputName) = Id(input)
    add("${inputName}_2x2_to_$outputName", CraftingRecipe.shaped(output, count) {
      pattern("XX", "XX")
      key("X", input)
    })
  }

  fun packed3x3(input: String, output: String, count: Int = 1) = Preset {
    val (_, outputName) = Id(output)
    val (_, inputName) = Id(input)
    add("${inputName}_3x3_to_$outputName", CraftingRecipe.shaped(output, count) {
      pattern("XXX", "XXX", "XXX")
      key("X", input)
    })
  }
}