package com.ionutciuta.challenges

import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools

class Day01(file: String) {
    private val data: Set<Int> = Input(file).readIntSet()

    private fun getSumPair(sum: Int, input: Set<Int>): Pair<Int, Int>? {
        for (number in input) {
            val diff = sum - number
            if (diff < 0 || !input.contains(diff)) {
                continue
            }
            println("Found pair $number & $diff for $sum")
            return Pair(number, diff)
        }
        return null
    }

    private fun getProduct(factors: Pair<Int, Int>?): Int {
        return if(factors != null) {
            factors.first * factors.second
        } else {
            -1
        }
    }

    fun solve() {
        val product = getProduct(getSumPair(2020, data))
        println(product)
    }

    private fun getSumTriple(sum: Int): Triple<Int, Int, Int>? {
        for (number in data) {
            val diff = sum - number
            if (diff < 0) {
                continue
            }

            val pair = getSumPair(diff, data) ?: continue

            return Triple(number, pair.first, pair.second)
        }
        return null
    }

    fun solvePart2() {
        val triple = getSumTriple(2020) ?: return
        println(getProduct(Pair(triple.first, triple.second)) * triple.third)
    }
}


fun main(args: Array<String>) {
    val file = Tools.getInput(args)

    Day01(file).solve()
    Day01(file).solvePart2()
}