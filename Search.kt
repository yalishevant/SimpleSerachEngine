package search

class Search(private val list: List<String>) {

    private val indexMap: MutableMap<String, List<Int>> = Index(list).map

    fun run() {
        while (true) {
            println()
            println(
                "=== Menu ===\n" +
                        "1. Find a person\n" +
                        "2. Print all people\n" +
                        "0. Exit"
            )

            when (scanner.nextLine().toInt()) {
                1    -> performSearch()
                2    -> printAll(list)
                0    -> return
                else -> println("Incorrect option! Try again.")
            }
        }
    }

    private fun performSearch() {

        println()
        println("Select a matching strategy: ALL, ANY, NONE")
        val strategy = Strategy.valueOf(scanner.nextLine())

        println()
        println("Enter a name or email to search all suitable people.")
        val query = scanner.nextLine()

        val fetchedIndexesByStrategy: Set<Int> = fetchListByStrategy(query, strategy)

        if (fetchedIndexesByStrategy.isNotEmpty()) {

            val message = if (fetchedIndexesByStrategy.size > 1) " persons found:"
            else " person found:"
            println("${fetchedIndexesByStrategy.size}" + message)

            for (index in fetchedIndexesByStrategy) {
                println(list[index])
            }
        } else
            println("No matching people found.")
    }

    private fun fetchListByStrategy(query: String, strategy: Strategy): Set<Int> {

        val words = query.split(" ").map { it.lowercase() }
        var finalListOfLineIndexes = mutableSetOf<Int>()

        when (strategy) {
            Strategy.ALL  -> {
                finalListOfLineIndexes = indexMap.values.flatten().toMutableSet()
                for (word in words) {
                    val elements = indexMap[word]?.toSet() ?: emptySet()
                    finalListOfLineIndexes = finalListOfLineIndexes.intersect(elements).toMutableSet()
                }
            }
            Strategy.ANY  -> {
                for (word in words) {
                    val elements: Set<Int> = indexMap[word]?.toSet() ?: emptySet()
                    finalListOfLineIndexes.addAll(elements)
                }
            }
            Strategy.NONE -> {
                finalListOfLineIndexes = indexMap.values.flatten().toMutableSet()
                for (word in words) {
                    val elements = indexMap[word]?.toSet() ?: emptySet()
                    finalListOfLineIndexes.removeAll(elements)
                }
            }
        }

        return finalListOfLineIndexes
    }

    private fun printAll(list: List<String>) {
        println()
        println("=== List of people ===")
        for (line in list) {
            println(line)
        }
    }

    enum class Strategy {
        ALL, ANY, NONE
    }
}