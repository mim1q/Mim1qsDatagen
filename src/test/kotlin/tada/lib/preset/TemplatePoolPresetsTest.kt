package tada.lib.preset

import org.junit.jupiter.api.Test
import tada.lib.TestUtil
import tada.lib.presets.common.TemplatePoolPresets

internal class TemplatePoolPresetsTest {
  @Test
  fun `prefixed template pool`() {
    TestUtil.assertJsonEquals(
      """
        {
          "name": "test:test_ns/some_structure",
          "fallback": "minecraft:empty",
          "elements": [
            {
              "weight": 1,
              "element": {
                "element_type": "minecraft:single_pool_element",
                "projection": "rigid",
                "location": "test:test_ns/some_structure/first",
                "processors": "minecraft:empty"
              }
            },
            {
              "weight": 2,
              "element": {
                "element_type": "minecraft:single_pool_element",
                "projection": "rigid",
                "location": "test:test_ns/some_structure/second",
                "processors": "minecraft:empty"
              }
            }
          ]
        }
      """,
      TemplatePoolPresets.prefixed("test:test_ns/some_structure", "first" to 1, "second" to 2).entries[0].resource.generate()
    )
  }


  @Test
  fun `indexed weighted`() {
    TestUtil.assertJsonEquals(
      """
        {
          "name": "test:test_ns/some_structure",
          "fallback": "minecraft:empty",
          "elements": [
            {
              "weight": 1,
              "element": {
                "element_type": "minecraft:single_pool_element",
                "projection": "terrain_matching",
                "location": "test:test_ns/some_structure/0",
                "processors": "minecraft:empty"
              }
            },
            {
              "weight": 2,
              "element": {
                "element_type": "minecraft:single_pool_element",
                "projection": "terrain_matching",
                "location": "test:test_ns/some_structure/1",
                "processors": "minecraft:empty"
              }
            },
            {
              "weight": 2,
              "element": {
                "element_type": "minecraft:single_pool_element",
                "projection": "terrain_matching",
                "location": "test:test_ns/some_structure/2",
                "processors": "minecraft:empty"
              }
            },
            {
              "weight": 2,
              "element": {
                "element_type": "minecraft:single_pool_element",
                "projection": "terrain_matching",
                "location": "test:test_ns/some_structure/3",
                "processors": "minecraft:empty"
              }
            }
          ]
        }
      """,
      TemplatePoolPresets.indexed("test:test_ns/some_structure", 1, 2, 2, 2, terrainMatching = true).entries[0].resource.generate()
    )
  }

  @Test
  fun `indexed unweighted`() {
    TestUtil.assertJsonEquals(
      """
        {
          "name": "test:test_ns/some_structure",
          "fallback": "minecraft:empty",
          "elements": [
            {
              "weight": 1,
              "element": {
                "element_type": "minecraft:single_pool_element",
                "projection": "rigid",
                "location": "test:test_ns/some_structure/0",
                "processors": "test:proc"
              }
            },
            {
              "weight": 1,
              "element": {
                "element_type": "minecraft:single_pool_element",
                "projection": "rigid",
                "location": "test:test_ns/some_structure/1",
                "processors": "test:proc"
              }
            },
            {
              "weight": 1,
              "element": {
                "element_type": "minecraft:single_pool_element",
                "projection": "rigid",
                "location": "test:test_ns/some_structure/2",
                "processors": "test:proc"
              }
            }
          ]
        }     
      """,
      TemplatePoolPresets.indexedUnweighted("test:test_ns/some_structure", 2, processors = "test:proc").entries[0].resource.generate()
    )
  }
}