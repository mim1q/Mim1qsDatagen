package tada.lib.resources.sound

import tada.lib.util.Id

fun SoundsJsonFile.simpleSound(id: String) {
  val (_, name) = Id(id)
  event(name) {
    sound(soundPath(id))
  }
}

fun SoundsJsonFile.emptySound(id: String) {
  val (_, name) = Id(id)
  event(name) {
    sound("fabric-sound-api-v1:empty")
  }
}

fun SoundsJsonFile.simpleVariedSound(id: String, variants: Int) {
  val (_, name) = Id(id)
  event(name) {
    for (i in 1..variants) {
      sound("${soundPath(id)}/$i")
    }
  }
}

fun soundPath(id: String) = id.replace(".", "/")