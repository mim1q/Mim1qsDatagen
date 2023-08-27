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

  fun copy(from: String, to: String) {
    val key = Id(from).toString()
    tags[key]?.values?.forEach {
      add(to, it)
    }
  }

  internal fun generatePreset() = Preset {
    tags.forEach {(k, v) -> run {
      val (_, name) = Id(k)
      add(name.split("/", limit = 2).last(), v)
    }}
  }
}