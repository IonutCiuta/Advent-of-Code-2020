package com.ionutciuta.challenges

import com.ionutciuta.tools.Challenge
import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools

class Day06(file: String): Challenge {
    private val data = Input(file).readLines()

    override fun solve() {
        val allGroupsYesQuestions = mutableListOf<Set<Char>>()

        var yesQuestions = mutableSetOf<Char>()
        for (line in data) {
            if(line.isBlank()) {
                allGroupsYesQuestions.add(yesQuestions)
                yesQuestions = mutableSetOf()
            } else {
                line.forEach {
                    if (!yesQuestions.contains(it))
                        yesQuestions.add(it)
                }
            }
        }
        allGroupsYesQuestions.add(yesQuestions)
        val r = allGroupsYesQuestions.map { it.size }.sum()
        println(r)
    }

    override fun solvePart2() {
        val allGroupsYesQuestions = mutableListOf<GroupResponses>()
        var groupResponses = GroupResponses()
        for (line in data) {
            if(line.isBlank()) {
                allGroupsYesQuestions.add(groupResponses)
                groupResponses = GroupResponses()
            } else {
                groupResponses.addMember()
                line.forEach {
                    groupResponses.addQ(it)
                }
            }
        }
        allGroupsYesQuestions.add(groupResponses)
        val r = allGroupsYesQuestions.map { it.getQsAnsweredByAllSum() }.sum()
        println(r)
    }
}

data class GroupResponses(
    private var memberCount: Int = 0,
    private val yesQuestions: MutableMap<Char, Int> = mutableMapOf()
) {
    fun addMember() {
        memberCount += 1
    }

    fun addQ(q: Char) {
        val count = yesQuestions[q] ?: 0
        yesQuestions[q] = (count + 1)
    }

    fun getQsAnsweredByAllSum() = yesQuestions
        .count { it.value == memberCount }

}

fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    Day06(file).solve()
    Day06(file).solvePart2()
}
