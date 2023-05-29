package tada.lib.lang

import com.google.gson.JsonElement
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tada.lib.generator.ResourceGenerator
import tada.lib.resources.blockstate.BlockState
import java.nio.file.Path

internal class LanguageHelperTest {
  private val mockFileSaver = object : ResourceGenerator.FileSaver {
    override fun prepare(baseDirectory: Path) {}
    override fun save(filePath: Path, content: String) {}
  }
  private val mockJsonFormatter = object : ResourceGenerator.JsonFormatter {
    override fun format(json: JsonElement): String = ""
  }

  @Test
  fun `language helper generates the correct list of names`() {
    val gen = ResourceGenerator("test", Path.of("test"), mockFileSaver, mockJsonFormatter)
    gen.add("block_one", BlockState.createSingle("test"))
    gen.add("second_block", BlockState.createSingle("test"))
    gen.add("anothertest_three", BlockState.createSingle("test"))
    val lang = LanguageHelper(Path.of("test"), Path.of("test"), mockFileSaver, mockJsonFormatter)
    val entries = lang.generateLangEntriesFromBlockstates(gen)
    assertEquals("block.test.block_one" to "Block One", entries[0])
    assertEquals("block.test.second_block" to "Second Block", entries[1])
    assertEquals("block.test.anothertest_three" to "Anothertest Three", entries[2])
  }
}