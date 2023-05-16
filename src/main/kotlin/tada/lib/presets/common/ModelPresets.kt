package tada.lib.presets.common

import tada.lib.presets.Preset
import tada.lib.resources.blockstate.BlockState
import tada.lib.resources.blockstate.BlockStateModel
import tada.lib.resources.blockstate.BlockStateModel.Rotation
import tada.lib.resources.model.ParentedModel
import tada.lib.util.Id

object CommonModelPresets {
  fun itemBlockModel(id: String): Preset {
    val (ns, name) = Id(id)
    return Preset {
      add(name, ParentedModel.item("$ns:block/$name"))
    }
  }

  fun generatedItemModel(id: String, folder: String = "item"): Preset {
    val (ns, name) = Id(id)
    return Preset {
      add(name, ParentedModel.item("item/generated").texture("layer0", "$ns:${folder}/$name"))
    }
  }

  fun cubeAllBlock(id: String): Preset {
    val (ns, name) = Id(id)
    return Preset {
      add(name, ParentedModel.block("block/cube_all").texture("all", "$ns:block/$name"))
      add(itemBlockModel(id))
      add(name, BlockState.createSingle("$ns:block/$name"))
    }
  }

  fun pillarBlock(id: String, end: String = "${id}_top", side: String = id): Preset {
    val (ns, name) = Id(id)
    val (eNs, eName) = Id(end)
    val (sNs, sName) = Id(side)
    return Preset {
      add(name, ParentedModel.block("block/cube_column")
      .texture("end", "$eNs:block/$eName")
      .texture("side", "$sNs:block/$sName")
      )
      add(itemBlockModel(id))
      add(name, BlockState.create {
        variant("axis=x", BlockStateModel("${ns}:block/$name", Rotation.CW_90, Rotation.CW_90))
        variant("axis=y", BlockStateModel("${ns}:block/$name"))
        variant("axis=z", BlockStateModel("${ns}:block/$name", Rotation.CW_90))
      })
    }
  }

  fun crossBlock(id: String) = Preset {
    val (ns, name) = Id(id)
    add(name, ParentedModel.block("block/cross")
      .texture("cross", "$ns:block/$name"))
    add(generatedItemModel("$ns:${name}_sapling", "block"))
  }

  fun stairsBlock(id: String, top: String = id, side: String = top, bottom: String = top): Preset {
    val (ns, name) = Id(id)
    val (tNs, tName) = Id(top)
    val (sNs, sName) = Id(side)
    val (bNs, bName) = Id(bottom)
    return Preset {
      add(itemBlockModel("${id}_stairs"))
      for (suffix in listOf(Pair("stairs", "stairs"), Pair("inner_stairs", "stairs_inner"), Pair("outer_stairs", "stairs_outer"))) {
        add("${name}_${suffix.second}", ParentedModel.block("block/${suffix.first}")
          .texture("top", "$tNs:block/$tName")
          .texture("side", "$sNs:block/$sName")
          .texture("bottom", "$bNs:block/$bName")
        )
      }
      add("${name}_stairs", BlockState.create {
        // Copied and reformatted from vanilla's stairs blockstate
        variant("facing=east,half=bottom,shape=inner_left", BlockStateModel("$ns:block/${name}_stairs_inner", Rotation.NONE, Rotation.CW_270, true))
        variant("facing=east,half=bottom,shape=inner_right", BlockStateModel("$ns:block/${name}_stairs_inner"))
        variant("facing=east,half=bottom,shape=outer_left", BlockStateModel("$ns:block/${name}_stairs_outer", Rotation.NONE, Rotation.CW_270, true))
        variant("facing=east,half=bottom,shape=outer_right", BlockStateModel("$ns:block/${name}_stairs_outer"))
        variant("facing=east,half=bottom,shape=straight", BlockStateModel("$ns:block/${name}_stairs"))
        variant("facing=east,half=top,shape=inner_left", BlockStateModel("$ns:block/${name}_stairs_inner", Rotation.CW_180, Rotation.NONE, true))
        variant("facing=east,half=top,shape=inner_right", BlockStateModel("$ns:block/${name}_stairs_inner", Rotation.CW_180, Rotation.CW_90, true))
        variant("facing=east,half=top,shape=outer_left", BlockStateModel("$ns:block/${name}_stairs_outer", Rotation.CW_180, Rotation.NONE, true))
        variant("facing=east,half=top,shape=outer_right", BlockStateModel("$ns:block/${name}_stairs_outer", Rotation.CW_180, Rotation.CW_90, true))
        variant("facing=east,half=top,shape=straight", BlockStateModel("$ns:block/${name}_stairs", Rotation.CW_180, Rotation.NONE, true))
        variant("facing=north,half=bottom,shape=inner_left", BlockStateModel("$ns:block/${name}_stairs_inner", Rotation.NONE, Rotation.CW_180, true))
        variant("facing=north,half=bottom,shape=inner_right", BlockStateModel("$ns:block/${name}_stairs_inner", Rotation.NONE, Rotation.CW_270, true))
        variant("facing=north,half=bottom,shape=outer_left", BlockStateModel("$ns:block/${name}_stairs_outer", Rotation.NONE, Rotation.CW_180, true))
        variant("facing=north,half=bottom,shape=outer_right", BlockStateModel("$ns:block/${name}_stairs_outer", Rotation.NONE, Rotation.CW_270, true))
        variant("facing=north,half=bottom,shape=straight", BlockStateModel("$ns:block/${name}_stairs", Rotation.NONE, Rotation.CW_270, true))
        variant("facing=north,half=top,shape=inner_left", BlockStateModel("$ns:block/${name}_stairs_inner", Rotation.CW_180, Rotation.CW_270, true))
        variant("facing=north,half=top,shape=inner_right", BlockStateModel("$ns:block/${name}_stairs_inner", Rotation.CW_180, Rotation.NONE, true))
        variant("facing=north,half=top,shape=outer_left", BlockStateModel("$ns:block/${name}_stairs_outer", Rotation.CW_180, Rotation.CW_270, true))
        variant("facing=north,half=top,shape=outer_right", BlockStateModel("$ns:block/${name}_stairs_outer", Rotation.CW_180, Rotation.NONE, true))
        variant("facing=north,half=top,shape=straight", BlockStateModel("$ns:block/${name}_stairs", Rotation.CW_180, Rotation.CW_270, true))
        variant("facing=south,half=bottom,shape=inner_left", BlockStateModel("$ns:block/${name}_stairs_inner"))
        variant("facing=south,half=bottom,shape=inner_right", BlockStateModel("$ns:block/${name}_stairs_inner", Rotation.NONE, Rotation.CW_90, true))
        variant("facing=south,half=bottom,shape=outer_left", BlockStateModel("$ns:block/${name}_stairs_outer"))
        variant("facing=south,half=bottom,shape=outer_right", BlockStateModel("$ns:block/${name}_stairs_outer", Rotation.NONE, Rotation.CW_90, true))
        variant("facing=south,half=bottom,shape=straight", BlockStateModel("$ns:block/${name}_stairs", Rotation.NONE, Rotation.CW_90, true))
        variant("facing=south,half=top,shape=inner_left", BlockStateModel("$ns:block/${name}_stairs_inner", Rotation.CW_180, Rotation.CW_90, true))
        variant("facing=south,half=top,shape=inner_right", BlockStateModel("$ns:block/${name}_stairs_inner", Rotation.CW_180, Rotation.CW_180, true))
        variant("facing=south,half=top,shape=outer_left", BlockStateModel("$ns:block/${name}_stairs_outer", Rotation.CW_180, Rotation.CW_90, true))
        variant("facing=south,half=top,shape=outer_right", BlockStateModel("$ns:block/${name}_stairs_outer", Rotation.CW_180, Rotation.CW_180, true))
        variant("facing=south,half=top,shape=straight", BlockStateModel("$ns:block/${name}_stairs", Rotation.CW_180, Rotation.CW_90, true))
        variant("facing=west,half=bottom,shape=inner_left", BlockStateModel("$ns:block/${name}_stairs_inner", Rotation.NONE, Rotation.CW_90, true))
        variant("facing=west,half=bottom,shape=inner_right", BlockStateModel("$ns:block/${name}_stairs_inner", Rotation.NONE, Rotation.CW_180, true))
        variant("facing=west,half=bottom,shape=outer_left", BlockStateModel("$ns:block/${name}_stairs_outer", Rotation.NONE, Rotation.CW_90, true))
        variant("facing=west,half=bottom,shape=outer_right", BlockStateModel("$ns:block/${name}_stairs_outer", Rotation.NONE, Rotation.CW_180, true))
        variant("facing=west,half=bottom,shape=straight", BlockStateModel("$ns:block/${name}_stairs", Rotation.NONE, Rotation.CW_180, true))
        variant("facing=west,half=top,shape=inner_left", BlockStateModel("$ns:block/${name}_stairs_inner", Rotation.CW_180, Rotation.CW_180, true))
        variant("facing=west,half=top,shape=inner_right", BlockStateModel("$ns:block/${name}_stairs_inner", Rotation.CW_180, Rotation.CW_270, true))
        variant("facing=west,half=top,shape=outer_left", BlockStateModel("$ns:block/${name}_stairs_outer", Rotation.CW_180, Rotation.CW_180, true))
        variant("facing=west,half=top,shape=outer_right", BlockStateModel("$ns:block/${name}_stairs_outer", Rotation.CW_180, Rotation.CW_270, true))
        variant("facing=west,half=top,shape=straight", BlockStateModel("$ns:block/${name}_stairs", Rotation.CW_180, Rotation.CW_180, true))
      })
    }
  }

  fun slabBlock(id: String, doubleModel: String = id, top: String = id, side: String = top, bottom: String = top): Preset {
    val (ns, name) = Id(id)
    val (tNs, tName) = Id(top)
    val (sNs, sName) = Id(side)
    val (bNs, bName) = Id(bottom)
    val (dNs, dName) = Id(doubleModel)
    return Preset {
      add(itemBlockModel("${id}_slab"))
      for (suffix in listOf("slab", "slab_top")) {
        add("${name}_$suffix", ParentedModel.block("block/$suffix")
          .texture("top", "$tNs:block/$tName")
          .texture("side", "$sNs:block/$sName")
          .texture("bottom", "$bNs:block/$bName")
        )
      }
      add("${name}_slab", BlockState.create {
        variant("type=bottom", "$ns:block/${name}_slab")
        variant("type=double", "$dNs:block/$dName")
        variant("type=top", "$ns:block/${name}_slab_top")
      })
    }
  }
  
  fun buttonBlock(id: String, texture: String = id): Preset {
    val (ns, name) = Id(id)
    val (tNs, tName) = Id(texture)
    return Preset {
      add("${name}_button", ParentedModel.block("block/button").texture("$tNs:block/$tName"))
      add("${name}_button_pressed", ParentedModel.block("block/button_pressed").texture("$tNs:block/$tName"))
      add("${name}_button", ParentedModel.item("block/button_inventory").texture("$tNs:block/$tName"))
      add("${name}_button", BlockState.create {
        for ((powered, suffix) in listOf(Pair("true", "_pressed"), Pair("false", ""))) {
          variant("face=ceiling,facing=east,powered=$powered", BlockStateModel("$ns:block/${name}_button$suffix", xRot = Rotation.CW_180, yRot = Rotation.CW_270))
          variant("face=ceiling,facing=north,powered=$powered", BlockStateModel("$ns:block/${name}_button$suffix", xRot = Rotation.CW_180, yRot = Rotation.CW_180))
          variant("face=ceiling,facing=south,powered=$powered", BlockStateModel("$ns:block/${name}_button$suffix", xRot = Rotation.CW_180))
          variant("face=ceiling,facing=west,powered=$powered", BlockStateModel("$ns:block/${name}_button$suffix", xRot = Rotation.CW_180, yRot = Rotation.CW_90))
          variant("face=floor,facing=east,powered=$powered", BlockStateModel("$ns:block/${name}_button$suffix", yRot = Rotation.CW_90))
          variant("face=floor,facing=north,powered=$powered", BlockStateModel("$ns:block/${name}_button$suffix"))
          variant("face=floor,facing=south,powered=$powered", BlockStateModel("$ns:block/${name}_button$suffix", yRot = Rotation.CW_180))
          variant("face=floor,facing=west,powered=$powered", BlockStateModel("$ns:block/${name}_button$suffix", yRot = Rotation.CW_270))
          variant("face=wall,facing=east,powered=$powered", BlockStateModel("$ns:block/${name}_button$suffix", uvlock = true, xRot = Rotation.CW_90, yRot = Rotation.CW_90))
          variant("face=wall,facing=north,powered=$powered", BlockStateModel("$ns:block/${name}_button$suffix", uvlock = true, xRot = Rotation.CW_90))
          variant("face=wall,facing=south,powered=$powered", BlockStateModel("$ns:block/${name}_button$suffix", uvlock = true, xRot = Rotation.CW_90, yRot = Rotation.CW_180))
          variant("face=wall,facing=west,powered=$powered", BlockStateModel("$ns:block/${name}_button$suffix", uvlock = true, xRot = Rotation.CW_90, yRot = Rotation.CW_270))
        }
      })
    }
  }

  fun pressurePlateBlock(id: String, texture: String = id): Preset {
    val (ns, name) = Id(id)
    val (tNs, tName) = Id(texture)
    return Preset {
      add(itemBlockModel("${id}_pressure_plate"))
      add("${name}_pressure_plate", ParentedModel.block("block/pressure_plate_up").texture("$tNs:block/$tName"))
      add("${name}_pressure_plate_down", ParentedModel.block("block/pressure_plate_down").texture("$tNs:block/$tName"))
      add("${name}_pressure_plate", BlockState.create {
        variant("powered=true", "$ns:block/${name}_pressure_plate_down")
        variant("powered=false", "$ns:block/${name}_pressure_plate")
      })
    }
  }
}