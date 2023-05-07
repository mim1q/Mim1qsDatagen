package tada.lib.resources.model

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import tada.lib.resources.MinecraftResource
import tada.lib.resources.model.ParentedModel.Type
import tada.lib.util.Id
import java.nio.file.Path

/**
 * A class representing a model of a block or item
 *
 * @param type the type of the model (either [Type.BLOCK] or [Type.ITEM]])
 * @param parent the parent model
 */
class ParentedModel internal constructor(
  private val type: Type,
  private val parent: String
) : MinecraftResource {
  /**
   * Map of the texture overrides for this model, where each key is the name and the value is the texture path
   */
  private val textureOverrides = mutableMapOf<String, String>()

  /**
   * Adds a texture override to the model
   *
   * @param key the name of the texture to override
   * @param texture path to the texture file
   * @return this [ParentedModel] instance
   */
  fun texture(key: String, texture: String): ParentedModel {
    textureOverrides[key] = Id(texture).toString()
    return this
  }

  /**
   * Adds a texture override to the model, with the defaul key of `texture`
   *
   * @param texture path to the texture file
   * @return this [ParentedModel] instance
   */
  fun texture(texture: String): ParentedModel {
    return texture("texture", texture)
  }

  override fun generate(): JsonElement {
    return JsonObject().apply {
      if (parent.isNotEmpty()) {
        addProperty("parent", parent)
      }
      if (textureOverrides.isNotEmpty()) {
        add("textures", JsonObject().apply {
          textureOverrides.forEach {
            addProperty(it.key, it.value)
          }
        })
      }
    }
  }

  override fun getDefaultOutputDirectory(baseDir: Path, namespace: String): Path {
    return baseDir.resolve("assets/$namespace/models/${type.folderName}/")
  }

  companion object {
    /**
     * Create a new Parented Model of an item
     *
     * @param parent the parent model
     * @param setup function to apply to the model on creation
     * @return a new instance of a [ParentedModel] with the [Type.ITEM] type property
     */
    fun item(parent: String, setup: ParentedModel.() -> Unit = {}): ParentedModel {
      return ParentedModel(Type.ITEM, parent).apply(setup)
    }

    /**
     * Create a new Parented Model of a block
     *
     * @param parent the parent model
     * @param setup function to apply to the model on creation
     * @return a new instance of a [ParentedModel] with the [Type.BLOCK] type property
     */
    fun block(parent: String, setup: ParentedModel.() -> Unit = {}): ParentedModel {
      return ParentedModel(Type.BLOCK, parent).apply(setup)
    }
  }

  /**
   * Model types, which determine whether a model should go into the items or blocks folder
   */
  enum class Type(val folderName: String) {
    ITEM("item"),
    BLOCK("block")
  }
}