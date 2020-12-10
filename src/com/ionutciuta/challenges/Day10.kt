package com.ionutciuta.challenges

import com.ionutciuta.tools.Challenge
import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools
import java.util.concurrent.atomic.AtomicInteger

class Day10(file: String): Challenge {
    private val data = Input(file).readIntSet().toMutableSet()
    private val deviceOffset = 3
    private val initialPortValue = 0
    private val portDiff = 3

    override fun solve() {
        data.add(0)
        data.add(getDeviceAdapter())
        val complete = data.sorted().toMutableSet()
        val counters = listOf(AtomicInteger(0), AtomicInteger(0), AtomicInteger(0))
        // println(complete)
        checkAdapters(0, 0, counters, complete)
        println(counters[0].get() * counters[2].get())
    }

    private fun checkAdapters(port: Int, i: Int, diffs: List<AtomicInteger>, ports: Set<Int>) {
        if(i == ports.size)
            return

        val next = getNextPorts(port)
        // println("Valid ports for $port are $next")
        next.forEachIndexed { ix, nextPort ->
            if(ports.contains(nextPort)) {
                val c = diffs[ix].incrementAndGet()
                // println("Next from $port is $nextPort with diff ${ix + 1}. Now total: $c\n")
                return checkAdapters(nextPort, i + 1, diffs, ports)
            }
        }
    }

    private fun getDeviceAdapter() = data.max()!! + deviceOffset

    private fun getNextPorts(port: Int) = (1..portDiff).map { it + port }.toList()

    override fun solvePart2() {
        println("Not yet")
    }

}

fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    val puzzle = Day10(file)
    puzzle.solve()
    puzzle.solvePart2()
}