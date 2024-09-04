package tada.lib.util


fun <T> Collection<T>.countByCriteria(vararg criteria: Pair<String, (T) -> Boolean>): Pair<Map<String, Int>, Int> {
  val result = mutableMapOf<String, Int>()
  criteria.forEach { result[it.first] = 0 }

  for (item in this) {
    criteria.firstOrNull { it.second.invoke(item) }?.let {
      result[it.first] = result[it.first]!! + 1
    }
  }

  return result.filter { it.value > 0 } to this.size - result.values.sum()
}