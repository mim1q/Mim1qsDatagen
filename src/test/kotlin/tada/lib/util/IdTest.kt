package tada.lib.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

internal class IdTest {
  @Test
  fun `destructuring works correctly`() {
    val id = Id("namespace", "name")
    val (ns, n) = id
    assertAll (
      { assertEquals(ns, "namespace") },
      { assertEquals(n, "name") }
    )
  }

  @Test
  fun `single-argument constructor works correctly`() {
    val (ns1, n1) = Id("namespace", "name")
    val (ns2, n2) = Id("namespace:name")
    assertAll (
      { assertEquals(ns1, ns2)},
      { assertEquals(n1, n2) },
    )
  }

  @Test
  fun `single-argument constructor provides the default namespace correctly`() {
    val id = Id("name")
    val (ns, _) = id
    assertEquals("minecraft", ns)
  }

  @Test
  fun `single-argument constructor throws when an invalid id is provided`() {
    assertThrows<IllegalArgumentException> { (Id("namespace:name:wrong")) }
  }

  @Test
  fun `tags are created correctly with one-argument constructor`() {
    assertEquals("#test:some_tag", Id.tag("test:some_tag").toString())
  }

  @Test
  fun `tags are created correctly with two-argument constructor`() {
    assertEquals("#test:some_tag", Id.tag("test", "some_tag").toString())
  }
}