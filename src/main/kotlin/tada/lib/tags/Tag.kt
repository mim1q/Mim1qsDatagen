package tada.lib.tags

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import tada.lib.resources.MinecraftResource
import tada.lib.util.Id
import java.nio.file.Path

class Tag(
  private val id: String,
  vararg entries: String
) : MinecraftResource() {
  internal val values = arrayListOf(*entries)

  fun add(vararg entries: String) {
    values.addAll(entries)
  }

  override fun generate(): JsonObject {
    return JsonObject().apply {
      addProperty("replace", false)
      add("values", JsonArray().apply {
        values.forEach {
          val id = Id(it)
          add( if (id.isTag) "#$id" else "$id" )
        }
      })
    }
  }

  override fun getDefaultOutputDirectory(baseDir: Path, namespace: String): Path {
    val (ns, name) = Id(id)
    return baseDir.resolve("data/$ns/tags/${name.split("/").first()}/")
  }
}