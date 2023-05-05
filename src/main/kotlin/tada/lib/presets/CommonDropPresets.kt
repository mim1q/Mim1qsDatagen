package tada.lib.presets

import tada.lib.presets.hardcoded.HardcodedDrops
import tada.lib.util.Id
import tada.lib.util.IntProvider

object CommonDropPresets {
  fun simpleDrop(id: Id, dropId: Id = id, count: IntProvider = IntProvider.constant(1)): Preset {
    val (_, name) = id
    return Preset {
      add(name, HardcodedDrops.simpleDrop(dropId, count))
    }
  }

  fun silkTouchDrop(id: Id, dropId: Id = id, silkTouchDropId: Id = id): Preset {
    val (_, name) = id
    return Preset {
      add(name, HardcodedDrops.silkTouchDrop(dropId, silkTouchDropId))
    }
  }
}