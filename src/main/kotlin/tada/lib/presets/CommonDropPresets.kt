package tada.lib.presets

import tada.lib.presets.hardcoded.HardcodedDrops
import tada.lib.util.Id
import tada.lib.util.IntProvider

object CommonDropPresets {
  fun simpleDrop(id: String, dropId: String = id, count: IntProvider = IntProvider.constant(1)): Preset {
    val (_, name) = Id(id)
    return Preset {
      add(name, HardcodedDrops.simpleDrop(dropId, count))
    }
  }

  fun silkTouchDrop(id: String, dropId: String = id, silkTouchDropId: String = id): Preset {
    val (_, name) = Id(id)
    return Preset {
      add(name, HardcodedDrops.silkTouchDrop(dropId, silkTouchDropId))
    }
  }

  fun silkTouchOnlyDrop(id: String): Preset {
    val (_, name) = Id(id)
    return Preset {
      add(name, HardcodedDrops.silkTouchOnlyDrop(id))
    }
  }
}