package mim1qsdatagen.base.blockstate

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import mim1qsdatagen.base.data.blockstate.BlockStateModel
import mim1qsdatagen.base.data.blockstate.MultipartBlockState
import mim1qsdatagen.base.data.blockstate.VariantBlockState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class BlockStateTest {
  private val gson = GsonBuilder().setPrettyPrinting().create()
  private val model1 = BlockStateModel("model1")
  private val model2 = BlockStateModel("model2")

  @Test
  fun `variant block state with no variants throws an exception`() {
    assertThrows<IllegalStateException> {
      VariantBlockState().generate()
    }
  }

  @Test
  fun `variant block state with one variant and one model`() {
    assertEquals(
      JsonObject().apply {
        add("variants", JsonObject().apply {
          add("", JsonObject().apply {
            addProperty("model", "model1")
          })
        })
      },
      VariantBlockState().variant("", "model1").generate()
    )
  }

  @Test
  fun `variant block state with one variant and multiple models`() {
    assertEquals(
      JsonObject().apply {
        add("variants", JsonObject().apply {
          add("", JsonArray().apply {
            add(model1.generate(true))
            add(model2.generate(true))
          })
        })
      },
      VariantBlockState().variant("", model1, model2).generate()
    )
  }

  @Test
  fun `variant block state with mutliple variants`() {
    assertEquals(
      JsonObject().apply {
        add("variants", JsonObject().apply {
          add("type=1", model1.generate(false))
          add("type=2", model2.generate(false))
        })
      },
      VariantBlockState()
        .variant("type=1", model1)
        .variant("type=2", model2)
        .generate()
    )
  }

  @Test
  fun `multipart block state with a single, always-applied model`() {
    assertEquals(
      JsonObject().apply {
        add("multipart", JsonArray().apply {
          add(JsonObject().apply {
            add("apply", model1.generate(false))
          })
        })
      },
      MultipartBlockState().apply(model1).generate()
    )
  }

  @Test
  fun `multipart block state with a conditionally-applied model`() {
    assertEquals(
      JsonObject().apply {
        add("multipart", JsonArray().apply {
          add(JsonObject().apply {
            add("apply", model1.generate(false))
            add("when", JsonObject().apply {
              addProperty("north", "true")
            })
          })
        })
      },
      MultipartBlockState().applyWhen(model1, "north=true").generate()
    )
  }

  @Test
  fun `multipart block state with a model applied when all conditions are met`() {
    assertEquals(
      JsonObject().apply {
        add("multipart", JsonArray().apply {
          add(JsonObject().apply {
            add("apply", model1.generate(false))
            add("when", JsonObject().apply {
              add("AND", JsonArray().apply {
                add(JsonObject().apply {
                  addProperty("north", "true")
                })
                add(JsonObject().apply {
                  addProperty("east", "false")
                })
              })
            })
          })
        })
      },
      MultipartBlockState().applyWhenAll(model1, "north=true", "east=false").generate()
    )
  }

  @Test
  fun `multipart block state with two models, each applied under different conditions`() {
    assertEquals(
      """
        {
          "multipart": [
            {
              "when": {
                "AND": [
                  {
                    "north": "true",
                    "east": "true"
                  },
                  {
                    "west": "false"
                  }
                ]
              },
              "apply": {
                "model": "model1"
              }
            },
            {
              "when": {
                "OR": [
                  {
                    "north": "false"
                  },
                  {
                    "east": "false"
                  }
                ]
              },
              "apply": {
                "model": "model2"
              }
            }
          ]
        }
      """.trimIndent(),
      gson.toJson(
        MultipartBlockState()
          .applyWhenAll(model1, "north=true, east=true", "west=false")
          .applyWhenAny(model2, "north=false", "east=false")
          .generate()
      )
    )
  }
}