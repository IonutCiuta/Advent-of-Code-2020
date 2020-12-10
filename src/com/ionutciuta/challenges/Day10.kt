package com.ionutciuta.challenges

import com.ionutciuta.tools.Challenge
import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools
import java.util.concurrent.atomic.AtomicInteger

class Day10(file: String): Challenge {
    private val data = Input(file).readIntSet().toMutableSet()
    private val deviceOffset = 3
    private val portDiff = 3
    private var complete = emptySet<Int>()

    init {
        data.add(0)
        data.add(getDeviceAdapter())
        complete = data.sorted().toMutableSet()
    }

    private fun getDeviceAdapter() = data.max()!! + deviceOffset

    private fun getNextPorts(port: Int) = (1..portDiff).map { it + port }.toList()

    override fun solve() {
        val counters = listOf(AtomicInteger(0), AtomicInteger(0), AtomicInteger(0))
        checkAdapters(0, counters, complete)
        println(counters[0].get() * counters[2].get())
    }

    private fun checkAdapters(port: Int, diffs: List<AtomicInteger>, ports: Set<Int>) {
        if(port == ports.last())
            return

        val next = getNextPorts(port)
        // println("Valid ports for $port are $next")
        next.forEachIndexed { ix, nextPort ->
            if(ports.contains(nextPort)) {
                val c = diffs[ix].incrementAndGet()
                // println("Next from $port is $nextPort with diff ${ix + 1}. Now total: $c\n")
                return checkAdapters(nextPort, diffs, ports)
            }
        }
    }

    override fun solvePart2() {
        val counter = AtomicInteger(0)
        checkAdaptersExhaustively(0, counter, complete)
        println(counter.get())
    }

    private fun checkAdaptersExhaustively(port: Int, routes: AtomicInteger, ports: Set<Int>) {
        if(port == ports.last()) {
            routes.incrementAndGet()
            return
        }

        val next = getNextPorts(port)
        next.forEach { nextPort ->
            if(ports.contains(nextPort)) {
                checkAdaptersExhaustively(nextPort, routes, ports)
            }
        }
    }
}

fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    val puzzle = Day10(file)
    puzzle.solve()
    puzzle.solvePart2()
}