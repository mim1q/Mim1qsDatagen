package tada.lib.presets.common

import tada.lib.presets.Preset
import tada.lib.resources.templatepool.TemplatePool
import tada.lib.util.Id

object TemplatePoolPresets {
  fun prefixed(
    prefix: String,
    vararg entries: Pair<String, Int>,
    processors: String? = null,
    terrainMatching: Boolean = false,
    id: String = prefix
  ) = Preset {
    val prefixId = Id(prefix).toString()
    val (_, name) = Id(id)
    add(name, TemplatePool.create(id) {
      for ((entry, weight) in entries) {
        single(weight, "$prefixId/$entry", processors, terrainMatching)
      }
    })
  }

  fun indexed(
    prefix: String,
    vararg weights: Int,
    processors: String? = null,
    terrainMatching: Boolean = false,
    id: String = prefix
  ) = prefixed(
    prefix,
    *weights.mapIndexed { i, weight -> "$i" to weight }.toTypedArray(),
    processors = processors,
    terrainMatching = terrainMatching,
    id = id
  )

  fun indexedUnweighted(
    prefix: String,
    maxIndex: Int,
    processors: String? = null,
    terrainMatching: Boolean = false,
    id: String = prefix
  ) = indexed(
    prefix,
    *IntArray(maxIndex + 1) { 1 },
    processors = processors,
    terrainMatching = terrainMatching,
    id = id
  )
}