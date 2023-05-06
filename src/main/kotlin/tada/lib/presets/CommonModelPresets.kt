package tada.lib.presets

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

  fun generatedItemModel(id: String): Preset {
    val (ns, name) = Id(id)
    return Preset {
      add(name, ParentedModel.item("item/generated").texture("layer0", "$ns:item/$name"))
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

  fun stairsBlock(id: String, top: String = id, side: String = top, bottom: String = top): Preset {
    val (ns, name) = Id(id)
    val (tNs, tName) = Id(top)
    val (sNs, sName) = Id(side)
    val (bNs, bName) = Id(bottom)
    return Preset {
      add(itemBlockModel("${id}_stairs"))
      for (suffix in listOf("stairs", "stairs_inner", "stairs_outer")) {
        add("${name}_$suffix", ParentedModel.block("block/$suffix")
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
}