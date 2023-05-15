package tada.lib.templatepool

import org.junit.jupiter.api.Test
import tada.lib.TestUtil
import tada.lib.resources.templatepool.TemplatePool

internal class TemplatePoolTest {
  @Test
  fun `simple template pool`() {
    TestUtil.assertJsonEquals(
      """
        {
          "name": "minecraft:test",
          "fallback": "test:test_fallback",
          "elements": [
            { "weight": 1, "element": { "element_type": "minecraft:empty_pool_element", "projection": "rigid" } },
            { "weight": 1, "element": { "element_type": "minecraft:feature_pool_element", "projection": "terrain_matching", "feature": "test:feature" } },
            { "weight": 3, "element": { "element_type": "minecraft:single_pool_element", "projection": "rigid", "location": "test:some_location", "processors": "test:processor" } }
          ]
        }
      """,
      TemplatePool.create("test", "test:test_fallback") {
        empty(1)
        feature(feature = "test:feature", terrainMatching =  true)
        single(3, "test:some_location", "test:processor")
      }.generate()
    )
  }

  @Test
  fun `template pool with list element`() {
    TestUtil.assertJsonEquals(
      """
        {
          "name": "minecraft:test_list",
          "fallback": "minecraft:empty",
          "elements": [
            {
              "weight": 2,
              "element": {
                "element_type": "minecraft:list_pool_element",
                "projection": "rigid",
                "elements": [
                  { "element_type": "minecraft:empty_pool_element", "projection": "rigid" },
                  { "element_type": "minecraft:feature_pool_element", "projection": "terrain_matching", "feature": "test:feature" },
                  { "element_type": "minecraft:single_pool_element", "projection": "rigid", "location": "test:some_location", "processors": "test:processor" }
                ]
              }
            }
          ]
        }
      """,
      TemplatePool.create("test_list") {
        list(2) {
          empty()
          feature(feature = "test:feature", terrainMatching =  true)
          single("test:some_location", "test:processor")
        }
      }.generate()
    )
  }
}