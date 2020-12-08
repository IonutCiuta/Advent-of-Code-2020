package com.ionutciuta.challenges

import com.ionutciuta.tools.Challenge
import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools

class Day08(file: String): Challenge {
    private val data = Input(file).readLines()


    override fun solve() {
        val r = Program(data).run()
        println(r)
    }

    override fun solvePart2() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class Program(val code: List<String>) {
    private val history = mutableSetOf<Int>()
    private var accumulator = 0

    fun run(): Int {
        var i = 0
        while (history.size != code.size) {
            if(history.contains(i)) break
            history.add(i)

            val line = code[i]
            val instr = getInstruction(line)
            val param = getParam(line)
            println("$instr, $param")

            when (instr) {
                "nop" -> i++
                "acc" -> {
                    accumulator += param
                    i++
                }
                "jmp" -> i += param
            }

        }
        return accumulator
    }

    private fun getInstruction(line: String) = line.substring(0, 3)

    private fun getParam(line: String) = line.substring(4).toInt()
}

fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    Day08(file).solve()
    Day08(file).solvePart2()
}