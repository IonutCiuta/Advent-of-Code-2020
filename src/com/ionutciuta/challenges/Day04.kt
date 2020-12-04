package com.ionutciuta.challenges

import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools

class Day04(file: String) {
    private val data = Input(file).readLines()

    private fun countValidPassports(): Int {
        var count = 0
        val passports = mutableListOf<Map<String, String>>()
        val passport = mutableMapOf<String, String>()

        for (line in data) {
            if(line.isEmpty()) {
                passports.add(passport)
                if(isValidPassport(passport)) {
                    count++
                }
                passport.clear()
                continue
            }

            line.split(" ").forEach { passportParam ->
                val paramParts = passportParam.split(":")
                passport[paramParts[0]] = paramParts[1]
            }
        }

        return count
    }

    private fun isValidPassport(passport: Map<String, String>): Boolean {
        PassportBlueprint.format.forEach {
            if(it.value && !passport.containsKey(it.key)) {
                return false
            }
        }

        return true
    }

    fun solve() {
        println(countValidPassports())
    }

    fun solvePart2() {
        println(countValidPassports())
    }
}

object PassportBlueprint {
    // field name -> mandatory?
    val format = mapOf(
        ("byr" to true),
        ("iyr" to true),
        ("eyr" to true),
        ("hgt" to true),
        ("hcl" to true),
        ("ecl" to true),
        ("pid" to true),
        ("cid" to false)
    )
}

fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    Day04(file).solve()
    Day04(file).solvePart2()
}