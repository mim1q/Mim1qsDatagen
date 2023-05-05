package tada.lib.presets.hardcoded

import com.google.gson.JsonElement
import tada.lib.resources.MinecraftResource
import java.nio.file.Path

class JsonResource(
  private val json: JsonElement,
  private val path: String,
  private val prefix: String = "assets"
) : MinecraftResource {
  override fun generate(): JsonElement = json

  override fun getDefaultOutputDirectory(baseDir: Path, namespace: String): Path {
    return baseDir.resolve("$prefix/$namespace/$path")
  }
}