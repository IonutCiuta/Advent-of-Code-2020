package com.ionutciuta.challenges

import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools

class Day02(file: String) {
    private val data = Input(file).readLines()

    fun solve() {
        val n = countValidPasswords(PassFilter.rangeFilter)
        println(n)
    }

    fun solvePar2() {
        val n = countValidPasswords(PassFilter.positionalFilter)
        println(n)
    }

    private fun countValidPasswords(filter: (String) -> Boolean) = data.filter { filter(it) }.size
}

object PassFilter {
    val rangeFilter = { text: String ->
        val passDef = PassDef(text)
        val count = passDef.pass.count { it == passDef.char }
        passDef.min <= count && count <= passDef.max
    }

    val positionalFilter = { text: String ->
        val passDef = PassDef(text)
        val first = passDef.pass[passDef.min - 1]
        val second = passDef.pass[passDef.max - 1]
        (first == passDef.char || second == passDef.char) && first != second
    }
}

class PassDef(text: String) {
    val pass: String
    val char: Char
    val min: Int
    val max: Int

    init {
        val textParts = parseText(text)
        val def = textParts[0]
        pass = textParts[1]

        val passDefParts = parseDef(def)
        val range = passDefParts[0]
        char = passDefParts[1][0]

        val rangeParts = parseRange(range)
        min = rangeParts[0]
        max = rangeParts[1]
    }

    private fun parseText(text: String): List<String> = text.split(": ")

    private fun parseDef(def: String): List<String> = def.split(" ")

    private fun parseRange(range: String): List<Int> = range.split("-").map { it.toInt() }.toList()
}

fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    Day02(file).solve()
    Day02(file).solvePar2()
}