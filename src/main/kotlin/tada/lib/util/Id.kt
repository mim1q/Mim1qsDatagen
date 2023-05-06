package tada.lib.util

/**
 * Represents the ID of a Resource, along with its name
 */
class Id {
  private val namespace: String
  private val name: String

  /**
   * @param namespace the namespace of the resource
   * @param name the name of the resource
   *
   * @constructor Creates a new [Id] with the given namespace and name
   */
  constructor(namespace: String, name: String) {
    this.namespace = namespace
    this.name = name
  }

  /**
   * @param id the id of the resource, in `"namespace:name"` format
   *
   * @constructor Creates a new [Id] with the namespace and name of the resource provided by the given [id] string
   */
  constructor(id: String) {
    val name = id.split(":")
    when (name.size) {
      1 -> {
        this.namespace = "minecraft"
        this.name = name[0]
      }
      2 -> {
        this.namespace = name[0]
        this.name = name[1]
      }
      else -> {
        throw IllegalArgumentException("Invalid id: $id")
      }
    }
  }

  /**
   * @return the namespace and name of the resource, in `"namespace:name"` format
   */
  override fun toString(): String = "$namespace:$name"

  /**
   * @return the namespace of the resource
   *
   * Used in destructuring:
   *
   * `val (ns, _) = Id("minecraft:stone")`
   */
  operator fun component1(): String = namespace

  /**
   * @return the name of the resource
   *
   * Used in destructuring:
   *
   * `val (_, name) = Id("minecraft:stone")`
   */
  operator fun component2(): String = name

  companion object {
    /**
     * @param namespace the namespace of the tag
     * @param name the name of the tag
     *
     * @return a new [Id] representing the tag with the given namespace and name (`#namespace:name`)
     */
    fun tag(namespace: String, name: String): Id = Id("#$namespace", name)

    /**
     * @param id the id of the tag, in `"namespace:name"` format
     *
     * @return a new [Id] representing the tag with the given namespace and name (`#namespace:name`)
     */
    fun tag(id: String) = Id("#${Id(id)}")
  }
}