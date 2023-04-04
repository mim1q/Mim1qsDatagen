package tada.lib.model

import tada.lib.TestUtil
import tada.lib.data.model.ParentedModel
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
      ParentedModel.item("").getDefaultOutputDirectory(Path.of("/"), "test"),
      Path.of("/assets/test/models/item/")
    )
    assertEquals(
      ParentedModel.block("").getDefaultOutputDirectory(Path.of("/"), "test"),
      Path.of("/assets/test/models/block/")
    )
  }
}