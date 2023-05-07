package tada.lib.tags

import tada.lib.presets.Preset
import tada.lib.util.Id

object TagManager {
  private val tags = hashMapOf<String, Tag>()

  internal fun clear() = tags.clear()

  fun add(tagId: String, vararg entries: String) {
    val key = Id(tagId).toString()
    if (tags.containsKey(key)) {
      tags[key]?.add(*entries)
      return
    }
    tags[key] = Tag(tagId, *entries)
  }

  internal fun generatePreset() = Preset { tags.forEach {(k, v) ->  add(k, v)} }
}