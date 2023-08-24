package tada.lib.util

import com.google.gson.JsonArray
import com.google.gson.JsonObject

class JsonBuilder {
  private val json = JsonObject()

  infix fun String.to(value: Number) = json.addProperty(this, value)
  infix fun String.to(value: String) = json.addProperty(this, value)
  infix fun String.to(value: Boolean) = json.addProperty(this, value)
  infix fun String.to(value: Char) = json.addProperty(this, value)
  infix fun String.to(value: JsonObject) = json.add(this, value)
  infix fun String.to(value: JsonArray) = json.add(this, value)

  operator fun String.invoke(setup: JsonBuilder.() -> Unit) = json.add(this, json(setup))
  val array: JsonArrayBuilder get() = JsonArrayBuilder()

  fun build() = json
}

class JsonArrayBuilder {
  private val json = JsonArray()

  operator fun get(vararg args: Any): JsonArray {
    args.forEach {
      when (it) {
        is Number -> json.add(it)
        is String -> json.add(it)
        is Boolean -> json.add(it)
        is Char -> json.add(it)
        is JsonObject -> json.add(it)
        is JsonArray -> json.add(it)
        else -> error("Invalid type")
      }
    }
    return json
  }

  fun add(vararg args: Any): JsonArray =  get(*args)

  val array: JsonArrayBuilder get() = JsonArrayBuilder()
  fun build() = json
}

fun json(setup: JsonBuilder.() -> Unit): JsonObject = JsonBuilder().apply(setup).build()
val jsonArray: JsonArrayBuilder get() = JsonArrayBuilder()