package tada.lib

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import org.junit.jupiter.api.Assertions.assertEquals

object TestUtil {
  private val gson = GsonBuilder().setPrettyPrinting().create()

  fun assertJsonEquals(expected: String, actual: JsonElement) {
    assertEquals(
      gson.toJson(JsonParser.parseString(expected).asJsonObject),
      gson.toJson(actual)
    )
  }

  fun assertJsonEquals(expected: JsonElement, actual: JsonElement) {
    assertEquals(
      gson.toJson(expected),
      gson.toJson(actual)
    )
  }
}