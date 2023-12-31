package tada.lib.presets.hardcoded

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import tada.lib.util.Id
import tada.lib.util.IntProvider

/**
 * This class contains hardcoded drop-generating functions, which are a quick and ugly workaround for assets that
 * can't be created with what I made so far.
 * Each function will be marked as deprecated once a proper API is created.
 * Use with caution, and preferably never.
 */
internal object HardcodedDrops {
  private fun jsonDrop(poolsSetup: JsonArray.() -> Unit): JsonResource {
    return JsonResource(
      JsonObject().apply {
        addProperty("type", "minecraft:block")
        add("pools", JsonArray().apply(poolsSetup))
      },
      "loot_tables/blocks",
      "data"
    )
  }

  private fun jsonStringDrop(text: String): JsonResource {
    return JsonResource(
      JsonParser.parseString(text).asJsonObject,
      "loot_tables/blocks",
      "data"
    )
  }

  private fun pool(rolls: IntProvider, entriesSetup: JsonArray.() -> Unit): JsonObject {
    return JsonObject().apply {
      add("rolls", rolls.generate())
      add("entries", JsonArray().apply(entriesSetup))
    }
  }

  private fun entry(
    name: String? = null,
    conditionsSetup: (JsonArray.() -> Unit)? = null,
    functionsSetup: (JsonArray.() -> Unit)? = null,
    setup:  JsonObject.() -> Unit = { },
    type: String = "minecraft:item"
  ): JsonObject {
    return JsonObject().apply {
      addProperty("type", type)
      if (name != null) {
        addProperty("name", Id(name).toString())
      }
      if (conditionsSetup != null) {
        add("conditions", JsonArray().apply(conditionsSetup))
      }
      if (functionsSetup != null) {
        add("functions", JsonArray().apply(functionsSetup))
      }
    }.apply(setup)
  }

  private fun silkTouchCondition(): JsonElement {
    return JsonObject().apply {
      addProperty("condition", "minecraft:match_tool")
      add("predicate", JsonObject().apply {
        add("enchantments",  JsonArray().apply {
          add(JsonObject().apply {
            addProperty("enchantment", "minecraft:silk_touch")
            addProperty("levels", 1)
          })
        })
      })
    }
  }

  private fun silkTouchOrShearsCondition(): JsonElement {
    return JsonObject().apply {
      addProperty("condition", "minecraft:any_of")
      add("terms", JsonArray().apply {
        add(JsonObject().apply {
          addProperty("condition", "minecraft:match_tool")
          add("predicate", JsonObject().apply {
            add("items", JsonArray().apply {
              add("minecraft:shears")
            })
          })
        })
        add(silkTouchCondition())
      })
    }
  }

  fun simpleDrop(id: String, rolls: IntProvider = IntProvider.constant(1)): JsonResource {
    return jsonDrop {
      add(pool(rolls) {
        add(entry(id))
      })
    }
  }

  fun silkTouchDrop(id: String, silkTouchId: String): JsonResource {
    return jsonDrop {
      add(pool(IntProvider.constant(1)) {
        add(entry(type = "minecraft:alternatives", setup = {
          add("children", JsonArray().apply {
            add(entry(silkTouchId, conditionsSetup = {
              add(silkTouchCondition())
            }))
            add(entry(id))
          })
        }))
      })
    }
  }

  fun silkTouchOnlyDrop(id: String): JsonResource {
    return jsonDrop {
      add(pool(IntProvider.constant(1))  {
        add(entry(id, conditionsSetup = {
          add(silkTouchCondition())
        }))
      })
    }
  }

  fun silkTouchOrShearsOnlyDrop(id: String): JsonResource {
    return jsonDrop {
      add(pool(IntProvider.constant(1))  {
        add(entry(id, conditionsSetup = {
          add(silkTouchOrShearsCondition())
        }))
      })
    }
  }

  fun doorDrop(id: String): JsonResource {
    return jsonDrop {
      add(pool(IntProvider.constant(1)) {
        add(entry(id, conditionsSetup = {
          add(JsonObject().apply {
            addProperty("block", id)
            addProperty("condition", "minecraft:block_state_property")
            add("properties", JsonObject().apply {
              addProperty("half", "lower")
            })
          })
        }))
      })
    }
  }

  fun slabDrop(id: String): JsonResource {
    return jsonDrop {
      add(pool(IntProvider.constant(1)){
        add(entry(id, functionsSetup = {
          add(JsonObject().apply {
            addProperty("function", "minecraft:set_count")
            addProperty("count", 2)
            addProperty("add", false)
            add("conditions", JsonArray().apply {
              add(JsonObject().apply {
                addProperty("block", Id(id).toString())
                addProperty("condition", "minecraft:block_state_property")
                add("properties", JsonObject().apply {
                  addProperty("type", "double")
                })
              })
            })
          })
        }))
      })
    }
  }

  fun leavesDrop(id: String, saplingId: String?): JsonResource {
    val saplingDrop = if (saplingId == null) "" else """,
            {
              "type": "minecraft:item",
              "conditions": [
                { "condition": "minecraft:survives_explosion" },
                { "chances": [ 0.05, 0.0625, 0.083333336, 0.1 ],
                  "condition": "minecraft:table_bonus",
                  "enchantment": "minecraft:fortune" }
              ],
              "name": "$saplingId"
            }"""
    return jsonStringDrop(
      """
        {
          "type": "minecraft:block",
          "pools": [
            {
              "bonus_rolls": 0.0,
              "entries": [
                {
                  "type": "minecraft:alternatives",
                  "children": [
                    {
                      "type": "minecraft:item",
                      "conditions": [
                        {
                          "condition": "minecraft:any_of",
                          "terms": [
                            { "condition": "minecraft:match_tool", "predicate": { "items": ["minecraft:shears"] } },
                            {
                              "condition": "minecraft:match_tool",
                              "predicate": { "enchantments": [{ "enchantment": "minecraft:silk_touch", "levels": { "min": 1 }}]}
                            }
                          ]
                        }
                      ],
                      "name": "$id"
                    }$saplingDrop
                  ]
                }
              ],
              "rolls": 1.0
            },
            {
              "bonus_rolls": 0.0,
              "conditions": [
                {
                  "condition": "minecraft:inverted",
                  "term": {
                    "condition": "minecraft:any_of",
                    "terms": [
                      { "condition": "minecraft:match_tool", "predicate": { "items": [ "minecraft:shears" ] } },
                      {
                        "condition": "minecraft:match_tool",
                        "predicate": { "enchantments": [ { "enchantment": "minecraft:silk_touch", "levels": { "min": 1 } }] }
                      }
                    ]
                  }
                }
              ],
              "entries": [
                {
                  "type": "minecraft:item",
                  "conditions": [
                    {
                      "chances": [0.02, 0.022222223, 0.025, 0.033333335, 0.1],
                      "condition": "minecraft:table_bonus",
                      "enchantment": "minecraft:fortune"
                    }
                  ],
                  "functions": [
                    {
                      "add": false,
                      "count": {
                        "type": "minecraft:uniform",
                        "max": 2.0,
                        "min": 1.0
                      },
                      "function": "minecraft:set_count"
                    },
                    {
                      "function": "minecraft:explosion_decay"
                    }
                  ],
                  "name": "minecraft:stick"
                }
              ],
              "rolls": 1.0
            }
          ]
        }
      """.trimIndent()
    )
  }
}