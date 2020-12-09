package com.ionutciuta.challenges

import com.ionutciuta.tools.Challenge
import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools

class Day09(file: String): Challenge {
    private val data = Input(file).readLines()
    var vulnerableNumber = 0L

    override fun solve() {
        vulnerableNumber = findVulnerableNumber(25)!!
        println(vulnerableNumber)
    }

    private fun findVulnerableNumber(preambleSize: Int): Long? {
        val preamble = Queue(preambleSize)
        val lookup = mutableSetOf<Long>()

        data.forEachIndexed { i, line ->
            val value = line.toLong()
            if(i >= preambleSize && !find(value, preamble.get(), lookup)) {
                return value
            }
            val old = preamble.add(value)
            lookup.remove(old)
            lookup.add(value)
        }
        return null
    }

    private fun find(n: Long, preamble: List<Long>, lookup: Set<Long>): Boolean {
        return preamble.map { lookup.contains(n - it) }.reduce { acc, b -> acc || b }
    }

    override fun solvePart2() {
        val numbers = data.map { it.toLong() }
        numbers.forEachIndexed { i, n ->
            val cache = mutableListOf<Long>()
            val found = findSublistSum(vulnerableNumber, i, numbers, cache)
            if(found && cache.size > 1) {
                println(getSumOfMinMax(cache))
                return
            }
        }
        println("Not found")
    }

    private fun findSublistSum(n: Long, i: Int, numbers: List<Long>, cache: MutableList<Long>): Boolean {
        if(i == numbers.size)
            return false

        val diff = n - numbers[i]
        cache.add(numbers[i])
        if(diff == 0L) {
            return true
        }

        return findSublistSum(diff, i+1, numbers, cache)
    }

    private fun getSumOfMinMax(list: List<Long>): Long {
        val sorted = list.sorted()
        return sorted.first() + sorted.last()
    }
}

class Queue(val size: Int) {
    private val q = mutableListOf<Long>()
    private var total = 0

    fun add(e: Long): Long {
        val r: Long

        if(total < size) {
            r = e
            q.add(e)
        } else {
            val i = total % size
            r = q[i]
            q[i] = e
        }

        total++
        return r
    }

    fun get() = q
}

fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    val puzzle = Day09(file)
    puzzle.solve()
    puzzle.solvePart2()
}