package com.ionutciuta.challenges

import com.ionutciuta.tools.Challenge
import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools

class Day05(file: String): Challenge {
    private val data = Input(file).readLines()

    private fun findRow(input: String): Int {
        var min = 0
        var max = 127
        input.forEach {
            val m = min + (max - min) / 2
            val e = max - min + 1
            when {
                it == 'F' -> max = m
                it == 'B' -> min = if(e % 2 == 0) m + 1 else m
            }
        }
//        println("Row $min, $max")
        return min
    }

    private fun findCol(input: String): Int {
        var min = 0
        var max = 7
        input.forEach {
            val m = min + (max - min) / 2
            val e = max - min + 1
            when {
                it == 'L' -> max = m
                it == 'R' -> min = if(e % 2 == 0) m + 1 else m
            }
        }
//        println("Col $min, $max")
        return min
    }

    private fun getSeatId(row: Int, col: Int): Int {
        return row * 8 + col
    }

    private fun findSeats(): Set<Int> {
        return data.map {
            val rowInfo = it.substring(0, 7)
            val colInfo = it.substring(7)
            val row = findRow(rowInfo)
            val col = findCol(colInfo)
            val seatId = getSeatId(row, col)
            println("$it, $row , $col, $seatId")
            seatId
        }.toSet()
    }

    fun findMaxSeat(): Int {
        return findSeats().toList().max()!!
    }

    override fun solve() {
        val r = findMaxSeat()
        println(r)
    }

    override fun solvePart2() {
        val seats = findSeats()
        val max = findMaxSeat()
        val min = seats.toList().min()!!
        for (seat in min..max) {
            if(!seats.contains(seat))
                println(seat)
        }
    }
}

fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    Day05(file).solve()
    Day05(file).solvePart2()
}

