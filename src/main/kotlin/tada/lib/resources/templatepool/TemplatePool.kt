package tada.lib.resources.templatepool

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import tada.lib.resources.MinecraftResource
import tada.lib.util.Id
import java.nio.file.Path

/**
 * Class representing a template pool.
 * Primarily used in Jigsaw structures, for determining which structures should be placed.
 */
class TemplatePool internal constructor(private val name: String, private val fallback: String) : MinecraftResource() {
  private val elements = mutableListOf<Pair<Int, Element>>()

  private fun element(element: Element, weight: Int = 1) {
    elements.add(weight to element)
  }

  /**
   * Adds an empty element to the pool.
   * @param weight Weight of the element
   * @see Element.Empty
   */
  fun empty(weight: Int = 1) = element(Element.Empty(), weight)

  /**
   * Adds a single element to the pool.
   * @param weight Weight of the element
   * @param location Location of the element's structure file
   * @param processors Processor list to be used on this element. `minecraft:empty` if not specified.
   * @param terrainMatching Whether to use terrain matching for this element.
   * @see Element.Single
   */
  fun single(weight: Int = 1, location: String, processors: String? = null, terrainMatching: Boolean = false) = element(Element.Single(location, processors, terrainMatching), weight)

  /**
   * Adds a list of elements to the pool.
   * @param weight Weight of the element
   * @param setup Setup function for the list
   * @see Element.List
   */
  fun list(weight: Int = 1, terrainMatching: Boolean = false, setup: Element.List.() -> Unit) = element(Element.List(terrainMatching).apply(setup), weight)

  /**
   * Adds a placed feature element to the pool.
   * @param weight Weight of the element
   * @param feature Location of the feature to be placed
   * @param terrainMatching Whether to use terrain matching for this element.
   * @see Element.Feature
   */
  fun feature(weight: Int = 1, feature: String, terrainMatching: Boolean = false) = element(Element.Feature(feature, terrainMatching), weight)


  override fun generate() = JsonObject().apply {
    addProperty("name", Id(name).toString())
    addProperty("fallback", Id(fallback).toString())
    add("elements", JsonArray().apply {
      elements.forEach {
        add(JsonObject().apply {
          addProperty("weight", it.first)
          add("element", it.second.generate())
        })
      }
    })
  }

  override fun getDefaultOutputDirectory(baseDir: Path, namespace: String): Path {
    return baseDir.resolve("data/$namespace/worldgen/template_pool")
  }

  companion object {
    /**
     * Creates a new template pool.
     * @param name Name of the pool
     * @param fallback Fallback pool to use if this pool cannot be used
     * @param setup Setup function for the pool
     */
    fun create(name: String, fallback: String = "minecraft:empty", setup: TemplatePool.() -> Unit): TemplatePool = TemplatePool(name, fallback).apply(setup)
  }

  /**
   * Represents an element in a template pool.
   * @param type Type of the element
   * @param terrainMatching Whether to use terrain matching for this element
   */
  sealed class Element(private val type: String, private val terrainMatching: Boolean = false) {
    fun generate() = JsonObject().apply {
      addProperty("element_type", Id(type).toString())
      addProperty("projection", if (terrainMatching) "terrain_matching" else "rigid")
      addData(this)
    }

    abstract fun addData(json: JsonObject)

    /**
     * Represents an empty element, which does not place anything
     */
    class Empty : Element("minecraft:empty_pool_element") {
      override fun addData(json: JsonObject) {}
    }

    /**
     * Represents an element that places a pre-existing placed feature
     * @param feature Location of the feature
     * @param terrainMatching Whether to use terrain matching for this element
     */
    class Feature(private val feature: String, terrainMatching: Boolean = false) : Element("minecraft:feature_pool_element", terrainMatching) {
      override fun addData(json: JsonObject) {
        json.addProperty("feature", Id(feature).toString())
      }
    }

    /**
     * Represents a list of elemenemnt that will be generated one after the other
     * @param terrainMatching Whether to use terrain matching for this element
     */
    class List(terrainMatching: Boolean = false) : Element("minecraft:list_pool_element", terrainMatching) {
      private val elements = mutableListOf<Element>()

      fun add(element: Element) {
        elements.add(element)
      }

      override fun addData(json: JsonObject) {
        json.add("elements", JsonArray().apply {
          elements.forEach {
            add(it.generate())
          }
        })
      }

      /**
       * Adds an empty element to the list
       * @see Empty
       */
      fun empty() = add(Empty())

      /**
       * Adds a single element to the list
       * @param location Location of the element's structure file
       * @param processors Processor list to be used on this element. `minecraft:empty` if not specified.
       * @param terrainMatching Whether to use terrain matching for this element.
       * @see Single
       */
      fun single(location: String, processors: String? = null, terrainMatching: Boolean = false) = add(Single(location, processors, terrainMatching))

      /**
       * Adds a list of elements to the list
       * @param terrainMatching Whether to use terrain matching for this element.
       * @param setup Setup function for the list
       * @see List
       */
      fun list(terrainMatching: Boolean = false, setup: List.() -> Unit) = add(List(terrainMatching).apply(setup))

      /**
       * Adds a placed feature element to the list
       * @param feature Location of the placed feature to be placed
       * @param terrainMatching Whether to use terrain matching for this element.
       * @see Feature
       */
      fun feature(feature: String, terrainMatching: Boolean = false) = add(Feature(feature, terrainMatching))
    }

    /**
     * Represents a single structure template element that will be placed
     * @param location Location of the element's structure file
     * @param processors Processor list to be used on this element. `minecraft:empty` if not specified.
     * @param terrainMatching Whether to use terrain matching for this element.
     */
    class Single(
      private val location: String,
      private val processors: String? = null,
      terrainMatching: Boolean = false
    ) : Element("minecraft:single_pool_element", terrainMatching) {
      override fun addData(json: JsonObject) {
        json.addProperty("location", Id(location).toString())
        json.addProperty("processors", processors?.let { Id(it).toString() } ?: "minecraft:empty")
      }
    }
  }
}