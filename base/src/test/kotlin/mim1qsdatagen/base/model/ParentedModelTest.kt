package mim1qsdatagen.base.model

import mim1qsdatagen.base.TestUtil
import mim1qsdatagen.base.data.model.ParentedModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Path

internal class ParentedModelTest {
  @Test
  fun `simple parented model`() {
    TestUtil.assertJsonEquals(
      """
        {
          "parent": "model1"
        }
      """.trimIndent(),
      ParentedModel(ParentedModel.Type.BLOCK, "model1").generate()
    )
  }

  @Test
  fun `parented model with one texture override`() {
    TestUtil.assertJsonEquals(
      """
        {
          "parent": "model1",
          "textures": {
            "layer0": "some_texture"
          }
        }
      """.trimIndent(),
      ParentedModel(ParentedModel.Type.ITEM, "model1")
        .texture("layer0", "some_texture")
        .generate()
    )
  }

  @Test
  fun `parented model with multiple texture overrides`() {
    TestUtil.assertJsonEquals(
      """
        {
          "parent": "model1",
          "textures": {
            "layer0": "some_texture",
            "layer1": "some_other_texture"
          }
        }
      """.trimIndent(),
      ParentedModel(ParentedModel.Type.ITEM, "model1")
        .texture("layer0", "some_texture")
        .texture("layer1", "some_other_texture")
        .generate()
    )
  }

  @Test
  fun `parented models of items and blocks point to the correct directory`() {
    assertEquals(
      ParentedModel.item("").getDefaultOutputPath(Path.of("/"), "test"),
      Path.of("/assets/test/models/item/")
    )
    assertEquals(
      ParentedModel.block("").getDefaultOutputPath(Path.of("/"), "test"),
      Path.of("/assets/test/models/block/")
    )
  }
}