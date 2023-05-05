package tada.lib.presets.hardcoded

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
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
      "loot_tables/blocks"
    )
  }

  private fun pool(rolls: IntProvider, entriesSetup: JsonArray.() -> Unit): JsonObject {
    return JsonObject().apply {
      add("rolls", rolls.generate())
      add("entries", JsonArray().apply(entriesSetup))
    }
  }

  private fun entry(
    name: Id? = null,
    conditionsSetup: (JsonArray.() -> Unit)? = null,
    functionsSetup: (JsonArray.() -> Unit)? = null,
    setup:  JsonObject.() -> Unit = { },
    type: String = "minecraft:item"
  ): JsonObject {
    return JsonObject().apply {
      addProperty("type", type)
      if (name != null) {
        addProperty("name", name.toString())
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

  fun simpleDrop(id: Id, rolls: IntProvider = IntProvider.constant(1)): JsonResource {
    return jsonDrop {
      add(pool(rolls) {
        add(entry(id))
      })
    }
  }

  fun silkTouchDrop(id: Id, silkTouchId: Id): JsonResource {
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
}