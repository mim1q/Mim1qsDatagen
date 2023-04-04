package tada.lib.data.generator

import com.google.gson.JsonElement
import tada.lib.data.MinecraftResource
import java.nio.file.Path

/**
 * Generator of the JSON files containing data provided by [MinecraftResource] objects.
 *
 * @param namespace the namespace of the generator (usually the ID of your mod / datapack)
 * @param baseDirectory path to the base directory of the generated data
 * @param fileSaver provides strategies to save the generated [JsonElement] Strings and to prepare the base directory
 * @param jsonFormatter provides a String formatting strategy for JSON data
 */
class ResourceGenerator internal constructor(
  private val namespace: String,
  private val baseDirectory: Path,
  private val fileSaver: FileSaver,
  private val jsonFormatter: JsonFormatter
) {
  /**
   * List of the provided [MinecraftResource]s with their respective [String] names
   */
  private val entries: MutableList<GeneratorEntry> = mutableListOf()

  /**
   * Add a resource to the generator
   *
   * @param name the name of the resource
   * @param resource the [MinecraftResource] to add to [entries]
   */
  fun add(name: String, resource: MinecraftResource) {
    entries.add(GeneratorEntry(name, resource))
  }

  /**
   * Add multiple resources to the generator
   *
   * @param entries [GeneratorEntry]s to add to [entries]
   */
  fun add(vararg entries: GeneratorEntry) {
    for (entry in entries) {
      add(entry.name, entry.resource)
    }
  }

  /**
   * Generate the single provided resource with the provided [fileSaver] and [jsonFormatter] strategies
   *
   * @param name the name of the resource
   * @param resource the [MinecraftResource] to generate
   */
  private fun generateResource(name: String, resource: MinecraftResource) {
    val filePath = resource.getDefaultOutputDirectory(baseDirectory, namespace).resolve(name);
    fileSaver.save(filePath, jsonFormatter.format(resource.generate()))
  }

  /**
   * Generate all the previously added resources, previously preparing
   */
  fun generate() {
    fileSaver.prepare(baseDirectory)
    for (entry in entries) {
      generateResource(entry.name, entry.resource)
    }
  }

  /**
   * Represents a [MinecraftResource] with its assigned name
   */
  data class GeneratorEntry(val name: String, val resource: MinecraftResource)

  /**
   * Defines file saving and base directory preparation strategies
   */
  interface FileSaver {
    /**
     * Prepares the root directory that will contain the generated resources
     *
     * @param baseDirectory path to the base directory of the generated pack
     */
    fun prepare(baseDirectory: Path)

    /**
     * Writes the provided content to a file in the given path
     *
     * @param filePath path to the file to generate
     * @param content content to write to the file
     */
    fun save(filePath: Path, content: String)
  }

  /**
   * Defines the JSON-to-string conversion strategy
   */
  interface JsonFormatter {
    /**
     * Converts the given JSON Element to a string
     *
     * @param json the [JsonElement] to convert
     * @return the String representation of the provided JSON Element
     */
    fun format(json: JsonElement): String
  }
}