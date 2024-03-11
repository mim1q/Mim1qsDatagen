package tada.lib.resources.sound

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import tada.lib.generator.ResourceGenerator
import tada.lib.resources.MinecraftResource
import tada.lib.resources.sound.SoundsJsonFile.SoundType
import java.nio.file.Path

class SoundsJsonFile internal constructor() : MinecraftResource() {
  private val soundEvents: ArrayList<SoundEvent> = arrayListOf()

  override fun generate() = JsonObject().apply {
    soundEvents.forEach { add(it.name, it.getJson()) }
  }

  override fun getDefaultOutputDirectory(baseDir: Path, namespace: String): Path
    = baseDir.resolve("assets/$namespace")

  fun event(name: String, replace: Boolean = false, subtitle: String? = null, setup: SoundEvent.() -> Unit) {
    soundEvents.add(SoundEvent(name, replace, subtitle).apply(setup))
  }

  class SoundEvent(
    val name: String,
    private val replace: Boolean,
    private val subtitle: String?
  ) {
    private val sounds: ArrayList<Sound> = arrayListOf()

    fun sound(
      name: String,
      volume: Float? = null,
      pitch: Float? = null,
      weight: Int? = null,
      stream: Boolean? = null,
      attenuationDistance: Int? = null,
      preload: Boolean? = null,
      type: SoundType? = null
    ) {
      sounds.add(Sound(name, volume, pitch, weight, stream, attenuationDistance, preload, type))
    }

    internal fun getJson() = JsonObject().apply {
      if (replace) addProperty("replace", true)
      if (subtitle != null) addProperty("subtitle", subtitle)
      add("sounds", JsonArray().apply {
        sounds.forEach { add(it.getJson()) }
      })
    }
  }

  class Sound(
    private val name: String,
    private val volume: Float?,
    private val pitch: Float?,
    private val weight: Int?,
    private val stream: Boolean?,
    private val attenuationDistance: Int?,
    private val preload: Boolean?,
    private val type: SoundType?
  ) {
    fun getJson(): JsonElement {
      val jsonObject = JsonObject().apply {
        if (volume != null) addProperty("volume", volume)
        if (pitch != null) addProperty("pitch", pitch)
        if (weight != null) addProperty("weight", weight)
        if (stream != null) addProperty("stream", stream)
        if (attenuationDistance != null) addProperty("attenuation_distance", attenuationDistance)
        if (preload != null) addProperty("preload", preload)
        if (type != null) addProperty("type", type.id)
      }
      if (jsonObject.isEmpty) return JsonPrimitive(name)
      jsonObject.addProperty("name", name)
      return jsonObject
    }
  }

  enum class SoundType(val id: String) {
    FILE("file"),
    EVENT("event")
  }
}

fun ResourceGenerator.sounds(setup: SoundsJsonFile.() -> Unit) = add("sounds", SoundsJsonFile().apply(setup))

fun test() {
  ResourceGenerator.create("", Path.of("")).sounds {
    event("test") {
      sound("test")
    }
    event("test2", true, "test") {
      sound("test2", 1f, 1f, 1, true, 1, true, SoundType.FILE)
    }
  }
}