package tada.lib.resources.loottable

import tada.lib.util.IntProvider

@Deprecated("Not implemented yet")
class LootTablePool(private val rolls: IntProvider, setup: LootTablePool.() -> Unit = {}) {
  constructor(rolls: Int, setup: LootTablePool.() -> Unit = {}): this(IntProvider.constant(rolls), setup)

  init {
    apply(setup)
  }
}