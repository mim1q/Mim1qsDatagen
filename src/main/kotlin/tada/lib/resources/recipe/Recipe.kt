package tada.lib.resources.recipe

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import tada.lib.resources.MinecraftResource
import tada.lib.util.Id
import java.nio.file.Path

/**
 * Base class for all recipe types
 *
 * @param type The type of recipe (e.g. `minecraft:crafting_shaped`, `minecraft:smelting`, `minecraft:blasting`, etc)
 */
abstract class Recipe(
  private val type: Id
) : MinecraftResource {
  override fun generate(): JsonElement {
    return JsonObject().apply {
      addProperty("type", type.toString())
    }.apply { addData(this) }
  }

  /**
   * Add the type-specific data to the recipe representation.
   * Override this method in subclasses to add the data.
   *
   * @param element The parent JSON object to add the data to
   */
  protected open fun addData(element: JsonObject) { }

  override fun getDefaultOutputDirectory(baseDir: Path, namespace: String): Path {
    return baseDir.resolve("data/$namespace/recipes/")
  }
}