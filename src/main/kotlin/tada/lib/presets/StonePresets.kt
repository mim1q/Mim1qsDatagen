package tada.lib.presets

import tada.lib.resources.blockstate.BlockState
import tada.lib.resources.blockstate.BlockStateModel
import tada.lib.resources.blockstate.BlockStateModel.Rotation
import tada.lib.resources.model.ParentedModel
import tada.lib.util.Id

object StonePresets {
  fun wall(id: String): Preset {
    val (ns, name) = Id(id)
    return Preset {
      add(CommonModelPresets.itemBlockModel("${id}_wall"))
      for (suffix in listOf("wall_post", "wall_side", "wall_side_tall")) {
        add("${name}_$suffix", ParentedModel.block("template_$suffix").texture("wall", "${ns}:block/${name}_wall"))
      }
      add("${name}_wall", BlockState.createMultipart {
        applyWhen(BlockStateModel("$ns:block/${name}_wall_post"), "up=true")
        applyWhen(BlockStateModel("$ns:block/${name}_wall_side"), "north=low")
        applyWhen(BlockStateModel("$ns:block/${name}_wall_side", yRot = Rotation.CW_90, uvlock = true), "east=low")
        applyWhen(BlockStateModel("$ns:block/${name}_wall_side", yRot = Rotation.CW_180, uvlock = true), "south=low")
        applyWhen(BlockStateModel("$ns:block/${name}_wall_side", yRot = Rotation.CW_270, uvlock = true), "west=low")
        applyWhen(BlockStateModel("$ns:block/${name}_wall_side_tall"), "north=tall")
        applyWhen(BlockStateModel("$ns:block/${name}_wall_side_tall", yRot = Rotation.CW_90, uvlock = true), "east=tall")
        applyWhen(BlockStateModel("$ns:block/${name}_wall_side_tall", yRot = Rotation.CW_180, uvlock = true), "south=tall")
        applyWhen(BlockStateModel("$ns:block/${name}_wall_side_tall", yRot = Rotation.CW_270, uvlock = true), "west=tall")
      })
    }
  }
}