package com.ionutciuta.challenges

import com.ionutciuta.tools.Challenge
import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools

class Day11(file: String): Challenge {
    private val data = Input(file).readCharMatrix()
    override fun solve() {
        val map = SeatMap(data)
        map.updatePart1()
        println(map.points.map { it.count { it == '#' } }.sum())
    }

    override fun solvePart2() {
        val map = SeatMap(data)
        map.updatePart2()
        println(map.points.map { it.count { it == '#' } }.sum())
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

    val getNeighbours = { row: Int, col: Int ->
        offsets
            .map { it.first + row to it.second + col }
            .filter { it.first in 0 until rows && it.second in 0 until cols }
            .map { points[it.first][it.second] }
            .toList()
    }

    fun isEmtpy(s: Char) = s == empty

    fun isOccupied(s: Char) = s == occupied

    fun isFloor(s: Char) = s == floor

    fun countTaken(n: List<Char>) = n.count { isOccupied(it) }

    fun getNext(s: Char, n: List<Char>, minOccupied: Int): Char {
        return when {
            isFloor(s) -> floor
            isEmtpy(s) && countTaken(n) == 0 -> { ocuppied += 1; occupied }
            isOccupied(s) && countTaken(n) > minOccupied -> empty
            else -> s
        }
    }

    fun updateOnce(neighborGen: (Int, Int) -> List<Char>, minOccupied: Int): Boolean {
        ocuppied = 0
        val new: Array<CharArray> = Array(rows) { i -> CharArray(cols) }
        for (i in 0 until rows) {
            val line = new[i]
            for (j in 0 until cols) {
                val n = neighborGen(i, j)
                val s = points[i][j]
                val next = getNext(s, n, minOccupied)
                line[j] = next
            }
        }
        val identical= areMapsIdentical(points, new)
        points = new
        return identical
    }

    fun updatePart1() {
        var iterations = 0
        while (!updateOnce(getNeighbours, 3)) { iterations++ }
        println("Stopped after $iterations iterations")
    }

    fun updatePart2() {
        var iterations = 0
        while (!updateOnce(getFirstNeighbours, 4)) { iterations++ }
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

    // Part 2 code - all of this needs refactoring
    fun getLefttNeighbour(i: Int, j: Int): Char {
        var left = j - 1
        while(left > -1) {
            val s = points[i][left]
            if(s != floor)
                return s
            left -= 1
        }
        return floor
    }

    fun getRightNeighbour(i: Int, j: Int): Char {
        var right = j + 1
        while(right < cols) {
            val s = points[i][right]
            if(s != floor)
                return s
            right += 1
        }
        return floor
    }

    fun getUpperNeighbour(i: Int, j: Int): Char {
        var up = i - 1
        while(up > -1) {
            val s = points[up][j]
            if(s != floor)
                return s
            up -= 1
        }
        return floor
    }

    fun getLowerNeighbour(i: Int, j: Int): Char {
        var up = i + 1
        while(up < rows) {
            val s = points[up][j]
            if(s != floor)
                return s
            up += 1
        }
        return floor
    }

    fun getUpLefttNeighbour(i: Int, j: Int): Char {
        var left = j - 1
        var up = i - 1
        while(left > -1 && up > -1) {
            val s = points[up][left]
            if(s != floor)
                return s
            left -= 1
            up -= 1
        }
        return floor
    }

    fun getDownLefttNeighbour(i: Int, j: Int): Char {
        var left = j - 1
        var down = i + 1
        while(left > -1 && down < rows) {
            val s = points[down][left]
            if(s != floor)
                return s
            left -= 1
            down += 1
        }
        return floor
    }

    fun getUpRightNeighbour(i: Int, j: Int): Char {
        var right = j + 1
        var up = i - 1
        while(right < cols && up > -1) {
            val s = points[up][right]
            if(s != floor)
                return s
            right += 1
            up -= 1
        }
        return floor
    }

    fun getDownRightNeighbour(i: Int, j: Int): Char {
        var right = j + 1
        var up = i + 1
        while(right < cols && up < rows) {
            val s = points[up][right]
            if(s != floor)
                return s
            right += 1
            up += 1
        }
        return floor
    }

    val getFirstNeighbours = { row: Int, col: Int ->
        listOf(
            getUpperNeighbour(row, col),
            getUpRightNeighbour(row, col),
            getRightNeighbour(row, col),
            getDownRightNeighbour(row, col),
            getLowerNeighbour(row, col),
            getDownLefttNeighbour(row, col),
            getLefttNeighbour(row, col),
            getUpLefttNeighbour(row, col)
        ).filter { !isFloor(it) }
    }
}


fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    val puzzle = Day11(file)
    puzzle.solve()
    puzzle.solvePart2()
}