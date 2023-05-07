package tada.lib.tag

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tada.lib.TestUtil
import tada.lib.tags.TagManager

internal class TagTest {
  @BeforeEach
  fun prepare() = TagManager.clear()

  @Test
  fun `tag manager generates a tag properly`() {
    TagManager.add("blocks/test", "#test:some_tag", "test:some_item", "stone", "#stones")
    val preset = TagManager.generatePreset()
    TestUtil.assertJsonEquals(
      """
      {
        "replace": false,
        "values": [
          "#test:some_tag",
          "test:some_item",
          "minecraft:stone",
          "#minecraft:stones"
        ]
      }
      """,
      preset.entries[0].resource.generate()
    )
  }

  @Test
  fun `tag manager generates a tag properly when updated multiple times`() {
    TagManager.add("blocks/test", "#test:some_tag", "test:some_item", "stone", "#stones")
    TagManager.add("blocks/test", "dirt", "#dirts")
    val preset = TagManager.generatePreset()
    TestUtil.assertJsonEquals(
      """
      {
        "replace": false,
        "values": [
          "#test:some_tag",
          "test:some_item",
          "minecraft:stone",
          "#minecraft:stones",
          "minecraft:dirt",
          "#minecraft:dirts"
        ]
      }
      """,
      preset.entries[0].resource.generate()
    )
  }
}