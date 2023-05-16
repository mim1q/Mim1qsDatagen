package tada.lib.presets.blocksets

import tada.lib.presets.Preset
import tada.lib.presets.common.CommonModelPresets
import tada.lib.resources.blockstate.BlockState
import tada.lib.resources.blockstate.BlockStateModel
import tada.lib.resources.blockstate.BlockStateModel.Rotation
import tada.lib.resources.model.ParentedModel
import tada.lib.util.Id

object WoodPresets {
  fun fence(id: String, base: String = id): Preset {
    val (ns, name) = Id(id)
    val (bNs, bName) = Id(base)
    return Preset {
      add("${name}_fence", ParentedModel.item("block/fence_inventory").texture("$bNs:block/$bName"))
      add("${name}_fence_side", ParentedModel.block("block/fence_side").texture("$bNs:block/$bName"))
      add("${name}_fence_post", ParentedModel.block("block/fence_post").texture("$bNs:block/$bName"))
      add("${name}_fence", BlockState.createMultipart {
        apply(BlockStateModel("$ns:block/${name}_fence_post"))
        applyWhen(BlockStateModel("$ns:block/${name}_fence_side", uvlock = true), "north=true")
        applyWhen(BlockStateModel("$ns:block/${name}_fence_side", yRot = Rotation.CW_90, uvlock = true), "east=true")
        applyWhen(BlockStateModel("$ns:block/${name}_fence_side", yRot = Rotation.CW_180, uvlock = true), "south=true")
        applyWhen(BlockStateModel("$ns:block/${name}_fence_side", yRot = Rotation.CW_270, uvlock = true), "west=true")
      })
    }
  }

  fun fenceGate(id: String, base: String = id): Preset {
    val (ns, name) = Id(id)
    val (bNs, bName) = Id(base)
    return Preset {
      add(CommonModelPresets.itemBlockModel("${id}_fence_gate"))
      for (suffix in listOf("fence_gate", "fence_gate_open", "fence_gate_wall", "fence_gate_wall_open")) {
        add("${name}_$suffix", ParentedModel.block("block/template_$suffix").texture("$bNs:block/$bName"))
      }
      add("${name}_fence_gate", BlockState.create {
        variant("facing=east,in_wall=false,open=false", BlockStateModel("$ns:block/${name}_fence_gate", yRot = Rotation.CW_270, uvlock = true))
        variant("facing=east,in_wall=false,open=true", BlockStateModel("$ns:block/${name}_fence_gate_open", yRot = Rotation.CW_270, uvlock = true))
        variant("facing=east,in_wall=true,open=false", BlockStateModel("$ns:block/${name}_fence_gate_wall", yRot = Rotation.CW_270, uvlock = true))
        variant("facing=east,in_wall=true,open=true", BlockStateModel("$ns:block/${name}_fence_gate_wall_open", yRot = Rotation.CW_270, uvlock = true))
        variant("facing=north,in_wall=false,open=false", BlockStateModel("$ns:block/${name}_fence_gate", yRot = Rotation.CW_180, uvlock = true))
        variant("facing=north,in_wall=false,open=true", BlockStateModel("$ns:block/${name}_fence_gate_open", yRot = Rotation.CW_180, uvlock = true))
        variant("facing=north,in_wall=true,open=false", BlockStateModel("$ns:block/${name}_fence_gate_wall", yRot = Rotation.CW_180, uvlock = true))
        variant("facing=north,in_wall=true,open=true", BlockStateModel("$ns:block/${name}_fence_gate_wall_open", yRot = Rotation.CW_180, uvlock = true))
        variant("facing=south,in_wall=false,open=false", BlockStateModel("$ns:block/${name}_fence_gate", uvlock = true))
        variant("facing=south,in_wall=false,open=true", BlockStateModel("$ns:block/${name}_fence_gate_open", uvlock = true))
        variant("facing=south,in_wall=true,open=false", BlockStateModel("$ns:block/${name}_fence_gate_wall", uvlock = true))
        variant("facing=south,in_wall=true,open=true", BlockStateModel("$ns:block/${name}_fence_gate_wall_open", uvlock = true))
        variant("facing=west,in_wall=false,open=false", BlockStateModel("$ns:block/${name}_fence_gate", yRot = Rotation.CW_90, uvlock = true))
        variant("facing=west,in_wall=false,open=true", BlockStateModel("$ns:block/${name}_fence_gate_open", yRot = Rotation.CW_90, uvlock = true))
        variant("facing=west,in_wall=true,open=false", BlockStateModel("$ns:block/${name}_fence_gate_wall", yRot = Rotation.CW_90, uvlock = true))
        variant("facing=west,in_wall=true,open=true", BlockStateModel("$ns:block/${name}_fence_gate_wall_open", yRot = Rotation.CW_90, uvlock = true))
      })
    }
  }

  fun door(id: String): Preset {
    val (ns, name) = Id(id)
    val suffixes = arrayListOf<String>()
    listOf("bottom", "top").forEach { a ->
      listOf("_left", "_right").forEach { b ->
        listOf("", "_open").forEach { c ->
          suffixes.add("door_$a$b$c")
        }
      }
    }
    return Preset {
      add(CommonModelPresets.generatedItemModel("${id}_door"))
      for (suffix in suffixes) {
        add("${name}_$suffix", ParentedModel.block("block/$suffix")
          .texture("bottom", "$ns:block/${name}_door_bottom")
          .texture("top", "$ns:block/${name}_door_top")
        )
      }
      add("${name}_door", BlockState.create {
        variant("facing=east,half=lower,hinge=left,open=false", BlockStateModel("$ns:block/${name}_door_bottom_left"))
        variant("facing=east,half=lower,hinge=left,open=true", BlockStateModel("$ns:block/${name}_door_bottom_left_open", yRot = Rotation.CW_90))
        variant("facing=east,half=lower,hinge=right,open=false", BlockStateModel("$ns:block/${name}_door_bottom_right"))
        variant("facing=east,half=lower,hinge=right,open=true", BlockStateModel("$ns:block/${name}_door_bottom_right_open", yRot = Rotation.CW_270))
        variant("facing=east,half=upper,hinge=left,open=false", BlockStateModel("$ns:block/${name}_door_top_left"))
        variant("facing=east,half=upper,hinge=left,open=true", BlockStateModel("$ns:block/${name}_door_top_left_open", yRot = Rotation.CW_90))
        variant("facing=east,half=upper,hinge=right,open=false", BlockStateModel("$ns:block/${name}_door_top_right"))
        variant("facing=east,half=upper,hinge=right,open=true", BlockStateModel("$ns:block/${name}_door_top_right_open", yRot = Rotation.CW_270))
        variant("facing=north,half=lower,hinge=left,open=false", BlockStateModel("$ns:block/${name}_door_bottom_left", yRot = Rotation.CW_270))
        variant("facing=north,half=lower,hinge=left,open=true", BlockStateModel("$ns:block/${name}_door_bottom_left_open"))
        variant("facing=north,half=lower,hinge=right,open=false", BlockStateModel("$ns:block/${name}_door_bottom_right", yRot = Rotation.CW_270))
        variant("facing=north,half=lower,hinge=right,open=true", BlockStateModel("$ns:block/${name}_door_bottom_right_open", yRot = Rotation.CW_180))
        variant("facing=north,half=upper,hinge=left,open=false", BlockStateModel("$ns:block/${name}_door_top_left", yRot = Rotation.CW_270))
        variant("facing=north,half=upper,hinge=left,open=true", BlockStateModel("$ns:block/${name}_door_top_left_open"))
        variant("facing=north,half=upper,hinge=right,open=false", BlockStateModel("$ns:block/${name}_door_top_right", yRot = Rotation.CW_270))
        variant("facing=north,half=upper,hinge=right,open=true", BlockStateModel("$ns:block/${name}_door_top_right_open", yRot = Rotation.CW_180))
        variant("facing=south,half=lower,hinge=left,open=false", BlockStateModel("$ns:block/${name}_door_bottom_left", yRot = Rotation.CW_90))
        variant("facing=south,half=lower,hinge=left,open=true", BlockStateModel("$ns:block/${name}_door_bottom_left_open", yRot = Rotation.CW_180))
        variant("facing=south,half=lower,hinge=right,open=false", BlockStateModel("$ns:block/${name}_door_bottom_right", yRot = Rotation.CW_90))
        variant("facing=south,half=lower,hinge=right,open=true", BlockStateModel("$ns:block/${name}_door_bottom_right_open"))
        variant("facing=south,half=upper,hinge=left,open=false", BlockStateModel("$ns:block/${name}_door_top_left", yRot = Rotation.CW_90))
        variant("facing=south,half=upper,hinge=left,open=true", BlockStateModel("$ns:block/${name}_door_top_left_open", yRot = Rotation.CW_180))
        variant("facing=south,half=upper,hinge=right,open=false", BlockStateModel("$ns:block/${name}_door_top_right", yRot = Rotation.CW_90))
        variant("facing=south,half=upper,hinge=right,open=true", BlockStateModel("$ns:block/${name}_door_top_right_open"))
        variant("facing=west,half=lower,hinge=left,open=false", BlockStateModel("$ns:block/${name}_door_bottom_left", yRot = Rotation.CW_180))
        variant("facing=west,half=lower,hinge=left,open=true", BlockStateModel("$ns:block/${name}_door_bottom_left_open", yRot = Rotation.CW_270))
        variant("facing=west,half=lower,hinge=right,open=false", BlockStateModel("$ns:block/${name}_door_bottom_right", yRot = Rotation.CW_180))
        variant("facing=west,half=lower,hinge=right,open=true", BlockStateModel("$ns:block/${name}_door_bottom_right_open", yRot = Rotation.CW_90))
        variant("facing=west,half=upper,hinge=left,open=false", BlockStateModel("$ns:block/${name}_door_top_left", yRot = Rotation.CW_180))
        variant("facing=west,half=upper,hinge=left,open=true", BlockStateModel("$ns:block/${name}_door_top_left_open", yRot = Rotation.CW_270))
        variant("facing=west,half=upper,hinge=right,open=false", BlockStateModel("$ns:block/${name}_door_top_right", yRot = Rotation.CW_180))
        variant("facing=west,half=upper,hinge=right,open=true", BlockStateModel("$ns:block/${name}_door_top_right_open", yRot = Rotation.CW_90))
      })
    }
  }

  fun trapdoor(id: String): Preset {
    val (ns, name) = Id(id)
    return Preset {
      add("${name}_trapdoor", ParentedModel.item("${ns}:block/${name}_trapdoor_bottom"))
      for (suffix in listOf("trapdoor_bottom", "trapdoor_open", "trapdoor_top")) {
        add("${name}_$suffix", ParentedModel.block("block/template_orientable_$suffix").texture("$ns:block/${name}_trapdoor"))
      }
      add("${name}_trapdoor", BlockState.create {
        variant("facing=east,half=bottom,open=false", BlockStateModel("$ns:block/${name}_trapdoor_bottom", yRot = Rotation.CW_90))
        variant("facing=east,half=bottom,open=true", BlockStateModel("$ns:block/${name}_trapdoor_open", yRot = Rotation.CW_90))
        variant("facing=east,half=top,open=false", BlockStateModel("$ns:block/${name}_trapdoor_top", yRot = Rotation.CW_90))
        variant("facing=east,half=top,open=true", BlockStateModel("$ns:block/${name}_trapdoor_open", xRot = Rotation.CW_180, yRot = Rotation.CW_270))
        variant("facing=north,half=bottom,open=false", BlockStateModel("$ns:block/${name}_trapdoor_bottom"))
        variant("facing=north,half=bottom,open=true", BlockStateModel("$ns:block/${name}_trapdoor_open"))
        variant("facing=north,half=top,open=false", BlockStateModel("$ns:block/${name}_trapdoor_top"))
        variant("facing=north,half=top,open=true", BlockStateModel("$ns:block/${name}_trapdoor_open", xRot = Rotation.CW_180, yRot = Rotation.CW_180))
        variant("facing=south,half=bottom,open=false", BlockStateModel("$ns:block/${name}_trapdoor_bottom", yRot = Rotation.CW_180))
        variant("facing=south,half=bottom,open=true", BlockStateModel("$ns:block/${name}_trapdoor_open", yRot = Rotation.CW_180))
        variant("facing=south,half=top,open=false", BlockStateModel("$ns:block/${name}_trapdoor_top", yRot = Rotation.CW_180))
        variant("facing=south,half=top,open=true", BlockStateModel("$ns:block/${name}_trapdoor_open", xRot = Rotation.CW_180))
        variant("facing=west,half=bottom,open=false", BlockStateModel("$ns:block/${name}_trapdoor_bottom", yRot = Rotation.CW_270))
        variant("facing=west,half=bottom,open=true", BlockStateModel("$ns:block/${name}_trapdoor_open", yRot = Rotation.CW_270))
        variant("facing=west,half=top,open=false", BlockStateModel("$ns:block/${name}_trapdoor_top", yRot = Rotation.CW_270))
        variant("facing=west,half=top,open=true", BlockStateModel("$ns:block/${name}_trapdoor_open", xRot = Rotation.CW_180, yRot = Rotation.CW_90))
      })
    }
  }
}