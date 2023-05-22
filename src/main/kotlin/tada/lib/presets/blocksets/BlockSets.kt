package tada.lib.presets.blocksets

import tada.lib.presets.Preset
import tada.lib.presets.common.CommonDropPresets
import tada.lib.presets.common.CommonModelPresets
import tada.lib.presets.common.CommonRecipePresets
import tada.lib.resources.blockstate.BlockState
import tada.lib.resources.model.ParentedModel
import tada.lib.resources.recipe.CraftingRecipe
import tada.lib.resources.recipe.StonecuttingRecipe
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
    add(CommonModelPresets.stairsBlock(id, "${id}_planks"))
    add(CommonModelPresets.slabBlock(id, "${id}_planks", "${id}_planks"))
    add(WoodPresets.fence(id, "${id}_planks"))
    add(WoodPresets.fenceGate(id, "${id}_planks"))
    add(WoodPresets.door(id))
    add(WoodPresets.trapdoor(id))
    add(CommonModelPresets.buttonBlock(id, "$ns:${name}_planks"))
    add(CommonModelPresets.pressurePlateBlock(id, "$ns:${name}_planks"))
    listOf("sign", "wall_sign").forEach {
      add("${name}_$it", BlockState.createSingle("$ns:block/${name}_$it"))
      add("${name}_$it", ParentedModel.block("").texture("particle", "$ns:block/${name}_planks"))
    }
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

    // Block Drops
    listOf(
      "${name}_planks", "${name}_log", "${name}_wood", "stripped_${name}_log", "stripped_${name}_wood", "${name}_slab",
      "${name}_stairs", "${name}_fence", "${name}_fence_gate", "${name}_trapdoor", "${name}_button", "${name}_pressure_plate"
    ).forEach {
      add(CommonDropPresets.simpleDrop("$ns:$it"))
    }
    listOf("${name}_sign", "${name}_wall_sign").forEach {
      add(CommonDropPresets.simpleDrop("$ns:$it", "$ns:${name}_sign"))
    }
    add(CommonDropPresets.doorDrop("$ns:${name}_door"))

    // Block Tags
    TagManager.add("blocks/planks", "$ns:${name}_planks")
    TagManager.add("$ns:blocks/${name}_logs", "$ns:${name}_log", "$ns:stripped_${name}_log", "$ns:${name}_wood", "$ns:stripped_${name}_wood")
    TagManager.add("blocks/logs_that_burn", "#$ns:${name}_logs")
    TagManager.add("blocks/fence_gates", "$ns:${name}_fence_gate")
    listOf("blocks/stairs", "blocks/wooden_stairs").forEach { TagManager.add(it, "$ns:${name}_stairs") }
    listOf("slab", "fence", "door", "trapdoor", "button", "pressure_plate").forEach {
      TagManager.add("blocks/wooden_${it}s", "$ns:${name}_${it}")
    }
    TagManager.add("blocks/standing_signs", "$ns:${name}_sign")
    TagManager.add("blocks/wall_signs", "$ns:${name}_wall_sign")

    // Item Tags
    TagManager.copy("$ns:blocks/${name}_logs", "$ns:items/${name}_logs")
    listOf(
      "planks", "${name}_logs", "logs_that_burn", "wooden_stairs", "wooden_slabs", "wooden_fences", "wooden_doors",
      "wooden_trapdoors", "wooden_buttons", "wooden_pressure_plates"
    ).forEach {
      TagManager.copy("blocks/$it", "items/$it")
    }
    TagManager.copy("blocks/standing_signs", "items/signs")
  }

  fun basicStoneSet(id: String, defaultDrop: Boolean = true, baseSuffix: String = "") = Preset {
    val (ns, name) = Id(id)
    // Models
    add(CommonModelPresets.cubeAllBlock("$ns:${name}$baseSuffix"))
    add(CommonModelPresets.stairsBlock(id, "$ns:${name}$baseSuffix"))
    add(CommonModelPresets.slabBlock(id, "$ns:${name}$baseSuffix", "$ns:${name}$baseSuffix"))
    add(StonePresets.wall(id, "$ns:${name}$baseSuffix"))
    // Recipes
    add(CommonRecipePresets.slab("$ns:${name}$baseSuffix", "$ns:${name}_slab"))
    add(CommonRecipePresets.stairs("$ns:${name}$baseSuffix", "$ns:${name}_stairs"))
    add("${name}_wall", CraftingRecipe.shaped("$ns:${name}_wall", 3) {
      pattern("XXX", "XXX")
      key("X", "$ns:${name}$baseSuffix")
    })
    add("${name}_slab_stonecutting", StonecuttingRecipe.create("$ns:${name}$baseSuffix", "$ns:${name}_slab", 2))
    add("${name}_stairs_stonecutting", StonecuttingRecipe.create("$ns:${name}$baseSuffix", "$ns:${name}_stairs"))
    add("${name}_wall_stonecutting", StonecuttingRecipe.create("$ns:${name}$baseSuffix", "$ns:${name}_wall"))
    // Block drops
    if (defaultDrop) {
      add(CommonDropPresets.simpleDrop("$ns:${name}$baseSuffix"))
    }
    listOf("stairs", "slab", "wall").forEach {
      add(CommonDropPresets.simpleDrop("$ns:${name}_$it"))
    }
    // Block Tags
    TagManager.add("blocks/stairs", "$ns:${name}_stairs")
    TagManager.add("blocks/mineable/pickaxe", "$ns:${name}_stairs", "$ns:${name}$baseSuffix")
    listOf("slab", "wall").forEach {
      TagManager.add("blocks/${it}s", "$ns:${name}_$it")
      TagManager.add("blocks/mineable/pickaxe", "$ns:${name}_$it")
    }
    // Item Tags
    listOf("slabs", "stairs", "walls").forEach {
      TagManager.copy("blocks/${it}", "items/${it}")
    }
  }

  fun fullStoneSet(id: String, defaultDrop: Boolean = true, baseSuffix: String = "") = Preset {
    val (ns, name) = Id(id)
    // Models
    add(basicStoneSet(id, defaultDrop, baseSuffix))
    add(CommonModelPresets.pressurePlateBlock(id))
    add(CommonModelPresets.buttonBlock(id))
    // Recipes
    add("${name}_pressure_plate", CraftingRecipe.shaped("$ns:${name}_pressure_plate", 1) {
      pattern("XX")
      key("X", "$ns:${name}$baseSuffix")
    })
    add("${name}_button", CraftingRecipe.shapeless("$ns:${name}_button") {
      ingredient("$ns:${name}$baseSuffix")
    })
    // Block Drops
    listOf("button", "pressure_plate").forEach {
      add(CommonDropPresets.simpleDrop("$ns:${name}_$it"))
    }
    // Block Tags
    TagManager.add("blocks/stone_pressure_plates", "$ns:${name}_pressure_plate")
    TagManager.add("blocks/mineable/pickaxe", "$ns:${name}_pressure_plate")
    TagManager.add("blocks/buttons", "$ns:${name}_button")
    TagManager.add("blocks/mineable/pickaxe", "$ns:${name}_button")
    // Item Tags
    listOf("stone_pressure_plates", "buttons").forEach {
      TagManager.copy("blocks/${it}", "items/${it}")
    }
  }
}