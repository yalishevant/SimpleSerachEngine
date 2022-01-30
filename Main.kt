package search

import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

val scanner = Scanner(System.`in`)

fun main(args: Array<String>) {

    val list = if (args.size > 1 && args[0] == "--data") {
        readFile(args[1])
    } else {
        readList()
    }

    val search = Search(list)
    search.run()

    println("Bye!")
}

fun readFile(path: String): List<String> {
    return Files.readAllLines(Paths.get(path))
}

fun readList(): List<String> {

    println("Enter the number of people:")
    val peopleNumber = scanner.nextLine().toInt()

    val list = mutableListOf<String>()
    println("Enter all people:")
    for (i in 1..peopleNumber) {
        list.add(scanner.nextLine())
    }
    return list
}
