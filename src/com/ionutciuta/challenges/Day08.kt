package com.ionutciuta.challenges

import com.ionutciuta.tools.Challenge
import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools

class Day08(file: String): Challenge {
    private val data = Input(file).readLines()


    override fun solve() {
        val r = LoopingProgram(data).run()
        println(r)
    }

    override fun solvePart2() {
        val r = SelfHealingProgram(data).run()
        println(r)
    }
}

class LoopingProgram(code: List<String>): Program(code) {
    override fun run(): Int {
        var i = 0
        while (history.size != code.size && i  < code.size) {
            if(history.contains(i)) {
                errored = true
                break
            }
            history.add(i)

            val line = code[i]
            val instr = getInstruction(line)
            val param = getParam(line)

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
}

class SelfHealingProgram(code: List<String>): Program(code) {

    override fun run(): Int {
        for ((i, line) in code.withIndex()) {
            val instr = getInstruction(line)
            val param = getParam(line)

            if(instr == "acc") {
                continue
            }

            val newInstr = switchInstr(instr)
            val newLine = "$newInstr $param"
            val newCode = code.toMutableList()
            newCode[i] = newLine
            val p = LoopingProgram(newCode)
            val r = p.run()

            if(!p.isErrored()) {
                return r
            }
        }
        return LoopingProgram(code).run()
    }

    private fun switchInstr(instr: String): String {
        return when(instr) {
            "nop" -> "jmp"
            "jmp" -> "nop"
            else -> instr
        }
    }
}

abstract class Program(val code: List<String>) {
    protected val history = mutableSetOf<Int>()
    protected var errored = false
    protected var accumulator = 0
    protected fun getInstruction(line: String) = line.substring(0, 3)
    protected fun getParam(line: String) = line.substring(4).toInt()
    fun isErrored() = errored

    abstract fun run(): Int
}

fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    Day08(file).solve()
    Day08(file).solvePart2()
}