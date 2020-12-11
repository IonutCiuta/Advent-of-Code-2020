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
    val empty = 'L'
    val taken = '#'
    val notSeat = '.'
    var ocuppied = 0

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

    fun isTaken(s: Char) = s == taken

    fun isNotSeat(s: Char) = s == notSeat

    fun countEmpty(n: List<Char>) = n.count { isEmtpy(it) }

    fun countTaken(n: List<Char>) = n.count { isTaken(it) }

    fun getNext(s: Char, n: List<Char>): Char {
        return when {
            isNotSeat(s) -> notSeat
            isEmtpy(s) && countTaken(n) == 0 -> { ocuppied += 1; taken }
            isTaken(s) && countTaken(n) > 3 -> empty
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
        //show(new)
        return if(compare(points, new)) {
            false
        } else {
            points = new
            true
        }

    }

    fun update() {
        var c = 0
        while (updateOnce()) {
            c++
        }
        println("Stopped after $c iterations")
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

    fun show(m: Array<CharArray>) {
        for (line in m) {
            for (col in line) {
                print(col)
            }
            println()
        }
        println()
    }

    fun compare(a: Array<CharArray>, b: Array<CharArray>): Boolean {
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