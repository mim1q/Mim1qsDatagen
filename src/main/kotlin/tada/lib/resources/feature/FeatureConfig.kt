package tada.lib.resources.feature

import com.google.gson.JsonObject
import tada.lib.resources.MinecraftResource
import java.nio.file.Path

class FeatureConfig : MinecraftResource() {
  override fun generate(): JsonObject {
    TODO("Not yet implemented")
  }

  override fun getDefaultOutputDirectory(baseDir: Path, namespace: String): Path {
    TODO("Not yet implemented")
  }
}