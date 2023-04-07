package tada.lib.blockstate

import com.google.gson.JsonObject
import tada.lib.resources.blockstate.BlockStateModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BlockStateModelTest {
  @Test
  fun `simple blockstate model`() {
    assertEquals(
      JsonObject().apply {
        addProperty("model", "minecraft:models/block/stone")
      },
      BlockStateModel("minecraft:models/block/stone").generate(false)
    )
  }

  @Test
  fun `simple blockstate model with weight`() {
    assertEquals(
      JsonObject().apply {
        addProperty("model", "minecraft:models/block/stone")
        addProperty("weight", 1)
      },
      BlockStateModel("minecraft:models/block/stone").generate(true)
    )
  }

  @Test
  fun `blockstate model with all properties not default`() {
    assertEquals(
      JsonObject().apply {
        addProperty("model", "minecraft:models/block/stone")
        addProperty("x", 90)
        addProperty("y", 270)
        addProperty("uvlock", true)
        addProperty("weight", 3)
      },
      BlockStateModel(
        "minecraft:models/block/stone",
        BlockStateModel.Rotation.CW_90,
        BlockStateModel.Rotation.CW_270,
        true,
        3
      ).generate(true)
    )
  }
}