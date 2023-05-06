package tada.lib.resources.recipe

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import tada.lib.resources.MinecraftResource
import tada.lib.util.Id
import java.nio.file.Path

abstract class Recipe(
  private val type: Id
) : MinecraftResource {
  override fun generate(): JsonElement {
    return JsonObject().apply {
      addProperty("type", type.toString())
    }.apply { addData(this) }
  }

  protected abstract fun addData(element: JsonObject)

  override fun getDefaultOutputDirectory(baseDir: Path, namespace: String): Path {
    return baseDir.resolve("data/$namespace/recipes/")
  }
}