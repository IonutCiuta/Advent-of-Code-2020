package com.ionutciuta.challenges

import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools

class Day03(file: String) {
    private val data = Input(file).readCharsInIntMatrix()
    private val width = data[0].size
    private val height = data.size

    private fun getTreesOnSlope(xSteps: Int, ySteps: Int): Int {
        val position = Position(0, 0)
        var count = 0

        while (position.y < height - 1) {
            count += moveFrom(position, xSteps, ySteps)
        }

        return count
    }

    private fun moveFrom(position: Position, xSteps: Int, ySteps: Int): Int {
        for (i in 1..xSteps) {
            stepHorizontally(position)
        }
        position.y += ySteps
        return get(position)
    }

    private fun stepHorizontally(position: Position) {
        val next = position.x + 1
        position.x = if(next < width) next else 0
    }

    private fun get(position: Position) = data[position.y][position.x]

    fun solve() {
        val count = getTreesOnSlope(3, 1)
        println(count)
    }

    fun solvePart2() {
        val slopes = listOf((1 to 1), (3 to 1), (5 to 1), (7 to 1), (1 to 2))
        val total = slopes.map { getTreesOnSlope(it.first, it.second).toLong() }.reduce { acc, i -> acc * i }
        println(total)
    }
}

data class Position(var x: Int, var y: Int)

fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    Day03(file).solve()
    Day03(file).solvePart2()
}