package tada.lib.lang

import blue.endless.jankson.Jankson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import tada.lib.resources.MinecraftResource
import java.io.File
import java.nio.file.Path
import java.util.*

class FlattenedJson(
  private val location: String,
  private val json: JsonObject,
  private val folder: String = "assets",
  private val separator: String = "."
) : MinecraftResource() {

  constructor(file: File, location: String, folder: String) : this(
    location,
    file.let {
      val jankson = Jankson.builder().build()
      val parsedJson = jankson.load(it)
      val normalizedJson = parsedJson.toJson(false, false)
      return@let JsonParser.parseString(normalizedJson).getAsJsonObject()
    },
    folder
  )


  override fun generate(): JsonObject = flatten(json, separator)

  override fun getDefaultOutputDirectory(baseDir: Path, namespace: String): Path =
    baseDir.resolve("$folder/$namespace/$location")
}

internal fun flatten(json: JsonObject, separator: String = "."): JsonObject {
  val result = JsonObject()
  json.entrySet().forEach { entry ->
    val keys = entry.key.split(';')
    keys.forEach { key ->
      if (entry.value.isJsonObject) {
        val flattened = flatten(entry.value.asJsonObject, separator)
        flattened.entrySet().forEach {
          val currentSeparator = if (it.key.isBlank()) "" else separator
          val newKey = applyKeyFlattening(key, it.key, currentSeparator)
          result.add(newKey, applyValueFlattening(it.value, newKey))
        }
      } else {
        result.add(key, applyValueFlattening(entry.value, key))
      }
    }
  }
  return result
}

private fun applyKeyFlattening(parent: String, child: String, separator: String): String {
  val slot = parent.indexOfFirst { it == '$' }
  return if (slot == -1) {
    "$parent$separator$child"
  } else {
    parent.substring(0, slot) + child + parent.substring(slot + 1)
  }
}

private fun applyValueFlattening(value: JsonElement, key: String): JsonElement {
  if (value is JsonPrimitive && value.isString && value.asString == "%") {
    val result = key
      .replace('_', ' ')
      .replace(Regex("(\\b)([a-z])")) { it.groupValues[1] + it.groupValues[2].uppercase(Locale.ENGLISH) }
    return JsonPrimitive(result)
  } else {
    return value
  }
}