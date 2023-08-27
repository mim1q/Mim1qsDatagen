package tada.lib.resources.loottable

import com.google.gson.JsonObject
import tada.lib.resources.MinecraftResource
import java.nio.file.Path

@Deprecated("Not implemented yet")
class LootTable : MinecraftResource() {
  override fun generate(): JsonObject {
    TODO("Not yet implemented")
  }

  override fun getDefaultOutputDirectory(baseDir: Path, namespace: String): Path {
    return baseDir.resolve("data/$namespace/loot_tables/")
  }

  enum class Type(val value: String) {
    EMPTY("minecraft:empty"),
    CHEST("minecraft:chest"),
    COMMAND("minecraft:command"),
    SELECTOR("minecraft:selector"),
    FISHING("minecraft:fishing"),
    ENTITY("minecraft:entity"),
    GIFT("minecraft:gift"),
    BARTER("minecraft:barter"),
    ADVANCEMENT_REWARD("minecraft:advancement_reward"),
    ADVANCEMENT_ENTITY("minecraft:advancement_entity"),
    GENERIC("minecraft:generic"),
    BLOCK("minecraft:block")
  }
}