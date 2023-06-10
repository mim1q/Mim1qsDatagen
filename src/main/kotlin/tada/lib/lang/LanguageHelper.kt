package tada.lib.lang

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import tada.lib.generator.BeautifiedJsonFormatter
import tada.lib.generator.FilesystemFileSaver
import tada.lib.generator.ResourceGenerator
import tada.lib.resources.blockstate.BlockState
import java.nio.file.Path

class LanguageHelper(
  private val langDirectoryPath: Path,
  private val helperDirectoryPath: Path,
  private val fileSaver: ResourceGenerator.FileSaver,
  private val jsonFormatter: ResourceGenerator.JsonFormatter
) {
  private val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()

  private fun String.snakeCaseToTitleCase() = split("_").joinToString(" ") {
    it.replaceFirstChar { ch -> ch.uppercase() }
  }

  internal fun generateLangEntriesFromBlockstates(generator: ResourceGenerator) = generator.entries
    .filter { it.resource is BlockState }
    .map { "block.${generator.namespace}.${it.name}" to it.name.snakeCaseToTitleCase() }

  private fun writeLangEntries(file: Path, generator: ResourceGenerator) {
    val result = JsonObject().apply {
      generateLangEntriesFromBlockstates(generator).forEach {
        addProperty(it.first, it.second)
      }
    }
    fileSaver.save(file, jsonFormatter.format(result))
  }

  fun automaticallyGenerateBlockEntries(generator: ResourceGenerator) {
    writeLangEntries(helperDirectoryPath.resolve("generated/generated_block_names.json"), generator)
  }

  private fun getLangEntriesFromFile(languageCode: String): List<Pair<String, String>> {
    val file = langDirectoryPath.resolve("$languageCode.json")
    if (!file.toFile().exists()) {
      throw IllegalStateException("Language file $file does not exist")
    }
    return JsonParser.parseReader(file.toFile().reader()).asJsonObject.entrySet().map {
      it.key to if (it.value.isJsonPrimitive) it.value.asString else ""
    }
  }

  private fun getMissingEntries(entries: List<Pair<String, String>>, base: List<Pair<String, String>>): List<Pair<String, String>> {
    return base.filter { !(
      entries.map { e -> e.first }.contains(it.first)
      || it.first.startsWith("_")
      || it.first.startsWith("comment")
    ) }
  }

  private fun getLanguageCodes(): List<String>? = langDirectoryPath.toFile().list()?.filter { it.endsWith(".json") }?.map { it.removeSuffix(".json") }

  fun generateMissingLangEntries(baseLanguage: String = "en_us") {
    val base = getLangEntriesFromFile(baseLanguage)
    getLanguageCodes()?.filter { it != baseLanguage }?.forEach {
      val entries = getMissingEntries(getLangEntriesFromFile(it), base)
      val jsonObject = JsonObject().apply {
        entries.forEach { e -> addProperty(e.first, e.second) }
      }
      fileSaver.save(helperDirectoryPath.resolve("$it.json"), gson.toJson(jsonObject))
    }
  }

  companion object {
    fun create(langDir: Path, helperDir: Path, setup: LanguageHelper.() -> Unit) =
      LanguageHelper(langDir, helperDir, FilesystemFileSaver, BeautifiedJsonFormatter).apply(setup)
  }
}