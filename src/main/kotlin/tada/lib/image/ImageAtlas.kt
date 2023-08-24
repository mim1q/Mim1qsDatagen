package tada.lib.image

import java.io.File
import javax.imageio.ImageIO

class ImageAtlas private constructor(
  atlas: File,
  private val outputDirectory: File,
  private val spriteWidth: Int = 16,
  private val spriteHeight: Int = 16
) {
  private val sprites = mutableSetOf<String>()
  private val image = ImageIO.read(atlas)

  fun sprite(name: String) = sprites.add(name)

  private fun verify() {
    if (sprites.isEmpty()) {
      throw IllegalStateException("At least one sprite must be defined")
    }
    if (image.width % spriteWidth != 0 || image.height % spriteHeight != 0) {
      throw IllegalStateException("Image width and height must be divisible by sprite width and height respectively")
    }
    if (outputDirectory.exists() && !outputDirectory.isDirectory) {
      throw IllegalStateException("Output directory must be a directory")
    }
    if (!outputDirectory.exists()) {
      outputDirectory.mkdirs()
    }
  }

  fun save() {
    val spriteCountHorizontal = image.width / spriteWidth

    for ((i, sprite) in sprites.withIndex()) {
      val minX = (i % spriteCountHorizontal) * spriteWidth
      val minY = (i / spriteCountHorizontal) * spriteHeight

      val subImage = image.getSubimage(minX, minY, spriteWidth, spriteHeight)
      val output = outputDirectory.resolve("$sprite.png")
      output.mkdirs()
      ImageIO.write(subImage, "png", output)
    }
  }

  companion object {
    fun create(
      atlas: File,
      outputDirectory: File,
      spriteWidth: Int = 16,
      spriteHeight: Int = 16,
      setup: ImageAtlas.() -> Unit
    ) = ImageAtlas(atlas, outputDirectory, spriteWidth, spriteHeight)
      .apply(setup)
      .also {
        it.verify()
      }

    fun createAndSave(
      atlas: File,
      outputDirectory: File,
      spriteWidth: Int = 16,
      spriteHeight: Int = 16,
      setup: ImageAtlas.() -> Unit
    ) = create(atlas, outputDirectory, spriteWidth, spriteHeight, setup)
      .also {
        it.save()
      }
  }
}