package tada.lib.generator

import com.google.gson.Gson
import com.google.gson.JsonElement
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tada.lib.data.blockstate.BlockState
import tada.lib.data.generator.ResourceGenerator
import tada.lib.data.model.ParentedModel
import tada.lib.data.presets.Preset
import java.nio.file.Path

class ResourceGeneratorTest {
  private val map = mutableMapOf<String, String>()
  private val mockFileSaver = object : ResourceGenerator.FileSaver {
    override fun prepare(baseDirectory: Path) {
      map.clear()
    }

    override fun save(filePath: Path, content: String) {
      map[filePath.toString()] = content
    }
  }

  private val mockJsonFormatter = object : ResourceGenerator.JsonFormatter {
    private val gson: Gson = Gson()

    override fun format(json: JsonElement): String {
     return gson.toJson(json)
    }
  }

  private fun createGenerator(): ResourceGenerator {
    return ResourceGenerator(
      "test",
      Path.of("/"),
      mockFileSaver,
      mockJsonFormatter
    )
  }

  @BeforeEach
  fun prepareTests() {
    map.clear()
  }

  @Test
  fun `a single file generates correctly`() {
    createGenerator().apply {
      add("test_model", ParentedModel.item(""))
    }.generate()
    assert(map.contains(Path.of("/assets/test/models/item/test_model.json").toString()))
  }

  @Test
  fun `multiple files generate correctly`() {
    createGenerator().apply {
      add("test_item", ParentedModel.item(""))
      add("test_block", ParentedModel.block(""))
      add("test_blockstate", BlockState.createSingle(""))
    }.generate()
    assert(map.contains(Path.of("/assets/test/models/item/test_item.json").toString()))
    assert(map.contains(Path.of("/assets/test/models/block/test_block.json").toString()))
    assert(map.contains(Path.of("/assets/test/blockstates/test_blockstate.json").toString()))
  }

  @Test
  fun `preset generates correctly`() {
    createGenerator().apply {
      add(Preset(

      ))
    }
  }
}