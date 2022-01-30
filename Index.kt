package search

class Index(list: List<String>) {
    val map: MutableMap<String, List<Int>>

    init {
        map = fullMapFormList(list)
    }

    private fun fullMapFormList(list: List<String>): MutableMap<String, List<Int>> {
        val map = mutableMapOf<String, List<Int>>()

        for (lineNumber in list.indices) {
            for (word in list[lineNumber].split(" ")) {
                val lowercaseWord = word.lowercase()
                if (map.containsKey(lowercaseWord)) {
                    val mutableList = map.getValue(lowercaseWord).toMutableList()
                    mutableList.add(lineNumber)
                    map[lowercaseWord] = mutableList
                } else {
                    map[lowercaseWord] = listOf(lineNumber)
                }
            }
        }

        return map
    }
}