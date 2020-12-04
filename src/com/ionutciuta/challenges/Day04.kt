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
            if(line.isBlank()) {
                passports.add(passport)
                if(isValidPassport(passport, true)) {
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

    private fun isValidPassport(passport: Map<String, String>, extraValidation: Boolean = false): Boolean {
        PassportBlueprint.format.forEach {
            if(it.value.isMandatory && !passport.containsKey(it.key)) {
                return false
            }

            if(extraValidation && it.value.isMandatory && !it.value.validation(passport[it.key]!!)) {
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
        ("byr" to PassportFieldRule(validation = {
            it.toInt() in 1920..2002
        })),
        ("iyr" to PassportFieldRule(validation = {
            it.toInt() in 2010..2020
        })),
        ("eyr" to PassportFieldRule(validation = {
            it.toInt() in 2020..2030
        })),
        ("hgt" to PassportFieldRule(validation = {
            when {
                it.contains("cm") -> isValidHeight("cm", 150, 193, it)
                it.contains("in") -> isValidHeight("in", 59, 76, it)
                else -> false
            }
        })),
        ("hcl" to PassportFieldRule(validation = {
            Regex("^(#[a-f0-9]{6})$").matches(it)
        })),
        ("ecl" to PassportFieldRule(validation = {
            setOf("amb","blu","brn","gry","grn","hzl","oth").contains(it)
        })),
        ("pid" to PassportFieldRule(validation = {
            Regex("[0-9]{9}").matches(it)
        })),
        ("cid" to PassportFieldRule(isMandatory = false, validation = { true }))
    )

    private fun isValidHeight(unit: String, min: Int, max: Int, input: String): Boolean {
        val split = input.split(unit)
        if (split.size != 2)
            return false

        var height = 0
        try {
            height = split[0].toInt()
        } catch (e: Exception) {
            return false
        }

        return height in min..max
    }
}

class PassportFieldRule(
    val isMandatory: Boolean = true,
    val validation: (String) -> Boolean
)

fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    Day04(file).solve()
    Day04(file).solvePart2()
}