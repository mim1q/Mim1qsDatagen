package mim1qsdatagen.base.blockstate

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import mim1qsdatagen.base.data.blockstate.BlockStateModel
import mim1qsdatagen.base.data.blockstate.VariantBlockState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class BlockStateEntryTest {
  private val model1 = BlockStateModel("model1")
  private val model2 = BlockStateModel("model2")

  @Test
  fun `variant block state entry with one model added`() {
    assertEquals(
      model1.generate(false),
      VariantBlockState.Entry("", model1).generateModelElement()
    )
  }

  @Test
  fun `variant block state entry with multiple models added returns an array`() {
    assertEquals(
      JsonArray().apply {
        add(model1.generate(true))
        add(model2.generate(true))
      },
      VariantBlockState.Entry("", model1, model2).generateModelElement()
    )
  }

  @Test
  fun `variant blocks state entry gets added to a parent correctly`() {
    val entry = VariantBlockState.Entry("", model1, model2)
    assertEquals(
      JsonObject().apply {
        add("", entry.generateModelElement())
      },
      JsonObject().apply {
        entry.addToParent(this)
      }
    )
  }
}