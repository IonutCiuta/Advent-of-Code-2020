package com.ionutciuta.challenges.day15

import com.ionutciuta.tools.Queue
import kotlin.collections.HashMap

class Day15Part1(input: String) {
    private val data = input.split(",").map { it.toInt() }.toList()

    fun solve(size: Int): Int {
        val history = HashMap<Int, Queue>()
        var prev = 0
        for (i in 0 until size) {
            if (i < data.size) {
                val occurrences = buildOccurrences(i)
                val current = data[i]
                history[current] = occurrences
                prev = current
                continue
            }

            val prevOcc = history[prev]!!
            val value = if(prevOcc.contentSize() < 2) { 0 } else { prevOcc.content[1] - prevOcc.content[0] }
            updateHistory(history, value, i)
            prev = value
        }
        return prev
    }

    private fun buildOccurrences(firstOccurrence: Int): Queue {
        val occurrences = Queue(2)
        occurrences.add(firstOccurrence)
        return occurrences
    }

    private fun updateHistory(history: HashMap<Int, Queue>, value: Int, index: Int) {
        val occurrences = history[value]
        if(occurrences == null) {
            history[value] = buildOccurrences(index)
        } else {
            occurrences.add(index)
        }
    }
}

fun main() {
    val size = 2020
    val puzzle = Day15Part1("2,20,0,4,1,17")

    val start = System.currentTimeMillis()
    var r = puzzle.solve(size)
    val end = System.currentTimeMillis()
    println("Part 1 - result $r, time ${end - start} millis")
}