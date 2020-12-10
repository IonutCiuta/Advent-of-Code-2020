package com.ionutciuta.challenges

import com.ionutciuta.tools.Challenge
import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools
import java.util.concurrent.atomic.AtomicInteger

class Day10(file: String): Challenge {
    private val data = Input(file).readIntSet().toMutableSet()
    private val deviceOffset = 3
    private val portDiff = 3
    private var complete = HashSet<Int>()
    private var dev = 0

    init {
        dev = getDeviceAdapter()
        data.add(0)
        data.add(dev)
        complete = data.sorted().toHashSet()
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
//        val counter = AtomicInteger(0)
//        checkAdaptersExhaustively(0, counter, complete)
//        println(counter.get())
        val c = Counter()
        cAE_2(0, complete, c)
        println(c.get())
    }

    class Counter {
        var v = 0L

        fun inc() {
            v++
        }

        fun add(x: Long) {
            v += x
        }

        fun get() = v
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

    val memo = HashMap<Int, Long>()

    private fun cAE_2(port: Int, ports: Set<Int>, counter: Counter) {
        if(port == dev) {
            counter.inc()
        }

        if(memo.containsKey(port)) {
            counter.add(memo[port]!!)
            return
        }

        val next = listOf(port + 1, port + 2, port + 3)
        next.forEach { nextPort ->
            if(ports.contains(nextPort)) {
                cAE_2(nextPort, ports, counter)
                memo[port] = counter.get()
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