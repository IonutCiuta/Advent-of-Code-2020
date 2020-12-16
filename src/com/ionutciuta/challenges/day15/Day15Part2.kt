package com.ionutciuta.challenges.day15

import kotlin.collections.HashMap
import kotlin.math.absoluteValue

class Day15Part2(input: String) {
    private val data = input.split(",").map { it.toInt() }.toList()

    private fun generateOccurrences(firstOccurrence: Int): SuperQueue {
        val occurrences = SuperQueue()
        occurrences.add(firstOccurrence)
        return occurrences
    }

    private fun updateHistory(history: HashMap<Int, SuperQueue>, value: Int, index: Int) {
        val occurrences = history[value]
        if(occurrences == null) {
            history[value] = generateOccurrences(index)
        } else {
            occurrences.add(index)
        }
    }

    fun solve(size: Int): Int {
        val history = HashMap<Int, SuperQueue>()
        val start = data.size
        var prev = 0

        data.forEachIndexed { i, _ ->
            val occurrences = generateOccurrences(i)
            val current = data[i]
            history[current] = occurrences
            prev = current
        }

        for (i in start until size) {
            val prevOcc = history[prev]!!
            val value = if(prevOcc.size() < 2) { 0 } else { prevOcc.diff() }
            updateHistory(history, value, i)
            prev = value
        }

        return prev
    }
}

private class SuperQueue {
    val array = Array(2) { 0 }
    var adds = 0
    var size = 0

    fun add(e: Int) {
        adds++
        if(size > 1) {
            size = 0
        }
        array[size] = e
        size += 1
    }

    fun size() = adds

    fun diff() = (array[1] - array[0]).absoluteValue
}

fun main() {
    val size = 30 * 100 * 100 * 100
    val puzzle = Day15Part2("2,20,0,4,1,17")

    val start = System.currentTimeMillis()
    var r = puzzle.solve(size)
    val end = System.currentTimeMillis()
    println("Part 2 - result $r, time ${end - start} millis")
}