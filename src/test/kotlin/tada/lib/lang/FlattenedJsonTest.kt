package tada.lib.lang

import org.junit.jupiter.api.Test
import tada.lib.TestUtil.assertJsonEquals
import tada.lib.util.json

internal class FlattenedJsonTest {
  @Test fun `flattened json generates the correct json`() {
    val toBeFlattened = json {
      "test." {
        "one_" {
          "a" to 5
          "b" to "abc"
        }
        "two" to true
      }
      "second" to 16
    }

    val expected = json {
      "test.one_a" to 5
      "test.one_b" to "abc"
      "test.two" to true
      "second" to 16
    }

    assertJsonEquals(expected, flatten(toBeFlattened, separator = ""))
  }
}