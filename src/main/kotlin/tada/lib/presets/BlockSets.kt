package tada.lib.presets

import tada.lib.util.Id

object BlockSets {
  fun woodSet(id: String) = Preset {
    val (ns, name) = Id(id)
    add(CommonModelPresets.cubeAllBlock("$ns:${name}_planks"))
    add(CommonModelPresets.pillarBlock("$ns:${name}_log"))
    add(CommonModelPresets.pillarBlock("$ns:stripped_${name}_log"))
    add(CommonModelPresets.pillarBlock("$ns:${name}_wood", "$ns:${name}_log", "$ns:${name}_log"))
    add(CommonModelPresets.pillarBlock("$ns:stripped_${name}_wood","$ns:stripped_${name}_log", "$ns:stripped_${name}_log"))
    add(CommonModelPresets.stairsBlock(id))
    add(CommonModelPresets.slabBlock(id, "${id}_planks"))
    add(WoodPresets.fence(id))
    add(WoodPresets.fenceGate(id))
    add(WoodPresets.door(id))
    add(WoodPresets.trapdoor(id))
    add(CommonModelPresets.buttonBlock(id, "$ns:${name}_planks"))
    add(CommonModelPresets.pressurePlateBlock(id, "$ns:${name}_planks"))
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