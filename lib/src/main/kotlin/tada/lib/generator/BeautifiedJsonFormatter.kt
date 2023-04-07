package tada.lib.generator

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement

/**
 * Implementation of [ResourceGenerator.JsonFormatter] that converts JSON Elements into pretty-printed strings
 */
object BeautifiedJsonFormatter : ResourceGenerator.JsonFormatter {
  private val gson = GsonBuilder().setPrettyPrinting().create()

  override fun format(json: JsonElement): String {
    return gson.toJson(json)
  }
}