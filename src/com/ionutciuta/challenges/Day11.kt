package com.ionutciuta.challenges

import com.ionutciuta.tools.Challenge
import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools

class Day11(file: String): Challenge {
    private val data = Input(file).readCharMatrix()
    override fun solve() {
        val map = SeatMap(data)
        map.update()
        println(map.points.map { it.count { it == '#' } }.sum())
    }

    override fun solvePart2() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class SeatMap(content: Array<CharArray>) {
    private val empty = 'L'
    private val occupied = '#'
    private val floor = '.'
    private var ocuppied = 0

    var points = content
    private val rows = content.size
    private val cols = content[0].size
    private val offsets = generateOffsets()

    fun neighbours(row: Int, col: Int) = offsets
            .map { it.first + row to it.second + col }
            .filter { it.first in 0 until rows && it.second in 0 until cols }
            .map { points[it.first][it.second] }
            .toList()


    fun isEmtpy(s: Char) = s == empty

    fun isOccupied(s: Char) = s == occupied

    fun isFloor(s: Char) = s == floor

    fun countTaken(n: List<Char>) = n.count { isOccupied(it) }

    fun getNext(s: Char, n: List<Char>): Char {
        return when {
            isFloor(s) -> floor
            isEmtpy(s) && countTaken(n) == 0 -> { ocuppied += 1; occupied }
            isOccupied(s) && countTaken(n) > 3 -> empty
            else -> s
        }
    }

    fun updateOnce(): Boolean {
        ocuppied = 0
        val new: Array<CharArray> = Array(rows) { i -> CharArray(cols) }
        for (i in 0 until rows) {
            val line = new[i]
            for (j in 0 until cols) {
                val n = neighbours(i, j)
                val s = points[i][j]
                val next = getNext(s, n)
                line[j] = next
            }
        }
        val identical= areMapsIdentical(points, new)
        points = new
        return identical

    }

    fun update() {
        var iterations = 0
        while (!updateOnce()) { iterations++ }
        println("Stopped after $iterations iterations")
    }

    private fun generateOffsets(): List<Pair<Int, Int>> {
        val offsets = mutableListOf<Pair<Int, Int>>()
        (-1..1).forEach { i ->
            (-1..1).forEach { j ->
                if (i != 0 || j != 0) {
                    offsets.add(i to j)
                }
            }
        }
        return offsets
    }

    fun show() {
        for (line in points) {
            for (col in line) {
                print(col)
            }
            println()
        }
        println()
    }

    private fun areMapsIdentical(a: Array<CharArray>, b: Array<CharArray>): Boolean {
        for (i in a.indices) {
            if (!a[i].contentEquals(b[i]))
                return false
        }
        return true
    }
}


fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    val puzzle = Day11(file)
    puzzle.solve()
    puzzle.solvePart2()
}