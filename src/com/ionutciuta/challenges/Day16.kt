package com.ionutciuta.challenges

import com.ionutciuta.tools.Challenge
import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools

class Day16(file: String): Challenge {
    private val data = Input(file).readLines()
    private val fields = mutableListOf<Field>()
    private var myTicket = listOf<Int>()
    private var otherTickets = mutableListOf<List<Int>>()
    private var invalidTicketsIndices = HashSet<Int>()

    init {
        readInput()
    }

    private fun readInput() {
        var i = 0
        while (data[i] != "") {
            fields.add(parseFieldDescription(data[i]))
            i++
        }

        i += 2
        myTicket = parseTicket(data[i])

        i += 3
        while (i < data.size) {
            otherTickets.add(parseTicket(data[i]))
            i++
        }
    }

    private fun parseTicket(input: String): List<Int> = input.split(",").map { it.toInt() }.toList()

    private fun parseFieldDescription(input: String): Field {
        val nameAndRules = input.split(":")
        val name = nameAndRules[0]
        val rules = nameAndRules[1].split("or")
            .map { it.trim() }
            .map { it.split("-") }
            .map { Interval(it[0].toInt(), it[1].toInt()) }
            .toList()
        return Field(name, rules)
    }

    private fun validateTicket(ticket: List<Int>): Int {
        var sum = 0
        ticket.map { validateTicketValue(it) }.forEachIndexed { index, value ->
            if(value == 0) {
                invalidTicketsIndices.add(index)
            }
            sum += value
        }
        return sum
    }

    private fun validateTicketValue(value: Int): Int {
        val r = fields.map { it.isValueValidForField(value) }.reduce { a, b -> a || b }
        return if(r) 0 else value
    }

    private fun cleanupTickets() {
        var validTickets = mutableListOf<List<Int>>()
        println("Before cleanup - ${otherTickets.size}")
        otherTickets.forEachIndexed { index, value ->
            if(!invalidTicketsIndices.contains(index))
                validTickets.add(value)
        }
        otherTickets = validTickets
        println("After cleanup - ${otherTickets.size}")
    }

    override fun solve() {
        val r = otherTickets.map { validateTicket(it) }.sum()
        println(r)
        cleanupTickets()
    }

    override fun solvePart2() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

data class Field(val name: String, private val rules: List<Interval> = mutableListOf()) {
    fun isValueValidForField(value: Int) = rules.map { it.contains(value) }.reduce { a, b -> a || b}
}

data class Interval(val min: Int, val max: Int) {
    fun contains(value: Int): Boolean = value in min..max
}

fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    val puzzle = Day16(file)
    puzzle.solve()
    puzzle.solvePart2()
}