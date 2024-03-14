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

  @Test fun `flattened semicolon-separated json generates correctly`() {
    val toBeFlattened = json {
      "test;test2;test3" {
        "a" to 5
        "b" to "abc"
      }
      "test.c" to 10
    }

    val expected = json {
      "test.a" to 5
      "test.b" to "abc"
      "test.c" to 10

      "test2.a" to 5
      "test2.b" to "abc"

      "test3.a" to 5
      "test3.b" to "abc"
    }

    assertJsonEquals(expected, flatten(toBeFlattened))
  }

  @Test fun `empty string doesn't append a separator`() {
    val toBeFlattened = json {
      "test" {
        "" to 5
        "b" to "abc"
      }
    }

    val expected = json {
      "test" to 5
      "test.b" to "abc"
    }

    assertJsonEquals(expected, flatten(toBeFlattened))
  }
}