package mim1qsdatagen.base

import com.google.gson.JsonElement
import com.google.gson.JsonParser

object TestUtil {
  fun jsonEquals(expected: String, actual: JsonElement): Boolean {
    return JsonParser.parseString(expected).asJsonObject.equals(actual)
  }
}