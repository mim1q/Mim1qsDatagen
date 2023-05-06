package tada.lib.preset

import org.junit.jupiter.api.Test
import tada.lib.TestUtil
import tada.lib.presets.CommonDropPresets

class CommonPresetsTest {
  @Test
  fun `simple item drop`() {
    TestUtil.assertJsonEquals(
      """
        {
          "type": "minecraft:block",
          "pools": [
            {
              "rolls": 1,
              "entries": [
                {
                  "type": "minecraft:item",
                  "name": "minecraft:stone"
                }
              ]
            }
          ]
        }
      """,
      CommonDropPresets.simpleDrop("stone", "stone").entries[0].resource.generate()
    )
  }

  @Test
  fun `simple silk touch item drop`() {
    TestUtil.assertJsonEquals(
      """
        {
          "type": "minecraft:block",
          "pools": [
            {
              "rolls": 1,
              "entries": [
                {
                  "type": "minecraft:alternatives",
                  "children": [
                    {
                      "type": "minecraft:item",
                      "name": "minecraft:grass_block",
                      "conditions": [
                        {
                          "condition": "minecraft:match_tool",
                          "predicate": {"enchantments": [{ "enchantment": "minecraft:silk_touch", "levels": 1 }]}
                        }
                      ]
                    },
                    {"type": "minecraft:item", "name": "minecraft:dirt"}
                  ]
                }
              ]
            }
          ]
        }
      """,
      CommonDropPresets.silkTouchDrop("grass_block", "dirt").entries[0].resource.generate()
    )
  }

  @Test
  fun `simple silk touch only drop`() {
    TestUtil.assertJsonEquals(
      """
      {
        "type": "minecraft:block",
        "pools": [
          {
            "rolls": 1,
            "entries": [
              {
                "type": "minecraft:item",
                "name": "test:test_block",
                "conditions": [
                  {
                    "condition": "minecraft:match_tool",
                    "predicate": {"enchantments": [{"enchantment": "minecraft:silk_touch", "levels": 1}]}
                  }
                ]
              }
            ]
          }
        ]
      }
      """,
      CommonDropPresets.silkTouchOnlyDrop("test:test_block").entries[0].resource.generate()
    )
  }
}