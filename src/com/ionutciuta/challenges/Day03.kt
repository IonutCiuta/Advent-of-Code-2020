package com.ionutciuta.challenges

import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools

class Day03(file: String) {
    private val data = Input(file).readCharMatrix()
    private val width = data[0].size
    private val height = data.size

    private fun countTrees(): Int {
        val position = Position(0, 0)
        var count = 0

        while (position.y < height - 1) {
            count += moveFrom(position)

        }

        return count
    }

    private fun moveFrom(position: Position): Int {
        var count = 0
        for (i in 1..3) {
            stepHorizontally(position)
        }
        count += oneStepVertically(position)
        return count
    }

    private fun stepHorizontally(position: Position) {
        val next = position.x + 1
        position.x = if(next < width) next else 0
    }

    private fun oneStepVertically(position: Position): Int {
        position.y++
        return get(position)
    }

    private fun get(position: Position) = data[position.y][position.x]

    fun solve() {
        val count = countTrees()
        println(count)
    }
}

data class Position(var x: Int, var y: Int)

fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    Day03(file).solve()
    //Day02(file).solvePar2()
}