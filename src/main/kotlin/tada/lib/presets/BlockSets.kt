package tada.lib.presets

import tada.lib.resources.blockstate.BlockState
import tada.lib.resources.model.ParentedModel
import tada.lib.resources.recipe.CraftingRecipe
import tada.lib.tags.TagManager
import tada.lib.util.Id

object BlockSets {
  fun fullWoodSet(id: String) = Preset {
    val (ns, name) = Id(id)
    basicWoodSet(id)
    // Models
    add(CommonModelPresets.generatedItemModel("$ns:${name}_boat"))
    add(CommonModelPresets.generatedItemModel("$ns:${name}_chest_boat"))
    add(CommonModelPresets.cubeAllBlock("$ns:${name}_leaves"))
    add(CommonModelPresets.crossBlock("$ns:${name}_sapling"))
    // Block Tags
    TagManager.add("blocks/leaves", "$ns:${name}_leaves")
    TagManager.add("blocks/saplings", "$ns:${name}_sapling")
    // Item Tags
    TagManager.copy("blocks/${name}_leaves", "items/${name}_leaves")
    TagManager.copy("blocks/${name}_saplings", "items/${name}_saplings")
    listOf("boat", "chest_boat").forEach {
      TagManager.add("items/${it}s", "${name}_${it}")
    }
  }

  fun basicWoodSet(id: String) = Preset {
    val (ns, name) = Id(id)

    // Models
    add(CommonModelPresets.cubeAllBlock("$ns:${name}_planks"))
    add(CommonModelPresets.pillarBlock("$ns:${name}_log"))
    add(CommonModelPresets.pillarBlock("$ns:stripped_${name}_log"))
    add(CommonModelPresets.pillarBlock("$ns:${name}_wood", "$ns:${name}_log", "$ns:${name}_log"))
    add(CommonModelPresets.pillarBlock("$ns:stripped_${name}_wood", "$ns:stripped_${name}_log", "$ns:stripped_${name}_log"))
    add(CommonModelPresets.stairsBlock(id))
    add(CommonModelPresets.slabBlock(id, "${id}_planks"))
    add(WoodPresets.fence(id))
    add(WoodPresets.fenceGate(id))
    add(WoodPresets.door(id))
    add(WoodPresets.trapdoor(id))
    add(CommonModelPresets.buttonBlock(id, "$ns:${name}_planks"))
    add(CommonModelPresets.pressurePlateBlock(id, "$ns:${name}_planks"))
    add("${name}_sign", BlockState.createSingle("$ns:block/${name}_sign"))
    add("${name}_sign", ParentedModel.block("").texture("particle", "$ns:${name}_planks"))
    add(CommonModelPresets.generatedItemModel("$ns:${name}_sign"))

    // Recipes
    add(CommonRecipePresets.oneToOne("#$ns:${name}_logs", "#$ns:${name}_planks", 4))
    add(CommonRecipePresets.slab("$ns:${name}_planks", "$ns:${name}_slab"))
    add(CommonRecipePresets.stairs("$ns:${name}_planks", "$ns:${name}_stairs"))
    add("${name}_fence", CraftingRecipe.shaped("$ns:${name}_fence", 3) {
      pattern("PSP", "PSP")
      key("P", "$ns:${name}_planks")
      key("S", "stick")
    })
    add("${name}_fence_gate", CraftingRecipe.shaped("$ns:${name}_fence_gate") {
      pattern("SPS", "SPS")
      key("P", "$ns:${name}_planks")
      key("S", "stick")
    })
    add("${name}_door", CraftingRecipe.shaped("$ns:${name}_door") {
      pattern("PP", "PP", "PP")
      key("P", "$ns:${name}_planks")
    })
    add("${name}_trapdoor", CraftingRecipe.shaped("$ns:${name}_trapdoor") {
      pattern("PPP", "PPP")
      key("P", "$ns:${name}_planks")
    })
    add("${name}_button", CraftingRecipe.shapeless("$ns:${name}_button") { ingredient("$ns:${name}_planks") })
    add("${name}_pressure_plate", CraftingRecipe.shaped("$ns:${name}_pressure_plate") {
      pattern("PP")
      key("P", "$ns:${name}_planks")
    })
    add("${name}_sign", CraftingRecipe.shaped("$ns:${name}_sign") {
      pattern("PPP", "PPP", " S ")
      key("P", "$ns:${name}_planks")
      key("S", "stick")
    })

    // Block Tags
    TagManager.add("blocks/planks", "$ns:${name}_planks")
    TagManager.add("$ns:blocks/${name}_logs", "$ns:${name}_log", "$ns:stripped_${name}_log", "$ns:${name}_wood", "$ns:stripped_${name}_wood")
    TagManager.add("blocks/logs_that_burn", "#$ns:${name}_logs")
    TagManager.add("blocks/fence_gates", "$ns:${name}_fence_gate")
    listOf("blocks/stairs", "blocks/wooden_stairs").forEach { TagManager.add(it, "$ns:${name}_stairs") }
    listOf("slab", "fence", "door", "trapdoor", "button", "pressure_plate").forEach {
      TagManager.add("blocks/wooden_${it}s", "$ns:${name}_${it}")
    }
    listOf("blocks/signs", "blocks/standing_signs").forEach { TagManager.add(it, "$ns:${name}_sign") }

    // Item Tags
    TagManager.copy("$ns:blocks/${name}_logs", "$ns:items/${name}_logs")
    listOf(
      "planks", "${name}_logs", "logs_that_burn", "fence_gates", "wooden_stairs", "wooden_slabs", "wooden_fences",
      "wooden_doors", "wooden_trapdoors", "wooden_buttons", "wooden_pressure_plates", "signs"
    ).forEach {
      TagManager.copy("blocks/$it", "items/$it")
    }
  }

  fun stoneSet(id: String, defaultDrop: Boolean = true) = Preset {
    val (ns, name) = Id(id)
    add(CommonModelPresets.cubeAllBlock("$ns:${name}"))
    add(CommonModelPresets.stairsBlock(id))
    add(CommonModelPresets.slabBlock(id))
    add(CommonModelPresets.pressurePlateBlock(id))
    add(CommonModelPresets.buttonBlock(id))
    add(StonePresets.wall(id))
  }

  fun fullStoneSet(id: String) = Preset {
    val (ns, name) = Id(id)
    add(stoneSet(id))
    add(stoneSet("${id}_cobblestone"))
    add(stoneSet("${id}_bricks"))
  }
}