package tada.lib.presets.hardcoded

import com.google.gson.JsonObject
import tada.lib.resources.MinecraftResource
import java.nio.file.Path

class JsonResource(
  private val json: JsonObject,
  private val path: String,
  private val prefix: String = "assets"
) : MinecraftResource() {
  override fun generate(): JsonObject = json

  override fun getDefaultOutputDirectory(baseDir: Path, namespace: String): Path {
    return baseDir.resolve("$prefix/$namespace/$path")
  }
}