package tada.lib.presets

import tada.lib.resources.blockstate.BlockState
import tada.lib.resources.blockstate.BlockStateModel
import tada.lib.resources.blockstate.BlockStateModel.Rotation
import tada.lib.resources.model.ParentedModel
import tada.lib.util.Id

object CommonModelPresets {
  fun itemBlockModel(id: Id): Preset {
    val (ns, name) = id
    return Preset {
      add(name, ParentedModel.item("$ns:block/$name"))
    }
  }

  fun generatedItemModel(id: Id): Preset {
    val (ns, name) = id
    return Preset {
      add(name, ParentedModel.item("item/generated").texture("layer0", "$ns:item/$name"))
    }
  }

  fun cubeAllBlockModel(id: Id): Preset {
    val (ns, name) = id
    return Preset {
      add(name, ParentedModel.block("block/cube_all").texture("all", "$ns:block/$name"))
    }
  }

  fun cubeAllBlock(id: Id): Preset {
    val (ns, name) = id
    return Preset {
      add(cubeAllBlockModel(id))
      add(itemBlockModel(id))
      add(name, BlockState.createSingle("$ns:block/$name"))
    }
  }

  fun stairsBlock(id: Id, top: Id, side: Id = top, bottom: Id = top): Preset {
    val (ns, name) = id
    val (tNs, tName) = top
    val (sNs, sName) = side
    val (bNs, bName) = bottom
    return Preset {
      add(itemBlockModel(id))
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
        variant("facing=east,half=top,shape=inner_left", BlockStateModel("$ns:block/${name}_stairs_inner", Rotation.CW_180, Rotation.NONE,  true))
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
}