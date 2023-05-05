package tada.lib.util

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive

sealed class IntProvider {
  abstract fun generate(): JsonElement

  companion object {
    fun constant(value: Int) = ConstantIntProvider(value)
    fun uniform(min: IntProvider, max: IntProvider) = UniformIntProvider(min, max)
    fun uniform(min: Int, max: Int) = uniform(constant(min), constant(max))
  }

  class ConstantIntProvider internal constructor(private val value: Int) : IntProvider() {
    override fun generate(): JsonElement {
      return JsonPrimitive(value)
    }
  }

  class UniformIntProvider internal constructor(
    private val min: IntProvider,
    private val max: IntProvider
  ) : IntProvider() {
    override fun generate(): JsonElement {
      return JsonObject().apply {
        add("min", min.generate())
        add("max", max.generate())
      }
    }
  }
}