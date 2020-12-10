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
        println(complete)
    }

    private fun checkAdapters(port: Int, diffs: List<AtomicInteger>, ports: Set<Int>) {
        if(port == ports.last())
            return

        val next = getNextPorts(port)
        next.forEachIndexed { ix, nextPort ->
            if(ports.contains(nextPort)) {
                diffs[ix].incrementAndGet()
                return checkAdapters(nextPort, diffs, ports)
            }
        }
    }

    override fun solvePart2() {
        val c = Counter()
        val memo = HashMap<Int, Long>()
        cAEmemo(0, complete, c, memo)
        println(c.get())
    }

    // This works well for short inputs, but can't handle a 100 line file
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

    // This is the same as above with some thin optimisations (no Atomic vars) & memoization
    private fun cAEmemo(port: Int, ports: Set<Int>, counter: Counter, memo: HashMap<Int, Long>) {
        // If we've reached the last element then we know
        // we have reached the end of the route
        if(port == dev) {
            counter.inc()
            return
        }

        // If it's not the end, let's check our cache and add the cached
        // value directly to our counter
        if(memo.containsKey(port)) {
            println("Found in cache $port > ${counter.get()}")
            counter.add(memo[port]!!)
            return
        }

        val next = listOf(port + 1, port + 2, port + 3)
        next.forEach { nextPort ->
            if(ports.contains(nextPort)) {
                cAEmemo(nextPort, ports, counter, memo)
                // Cache the response - we have returned from the recursive call
                // Our pivot/key is the port and the value is what we've counted until now
                println("Caching $port > ${counter.get()}")
                memo[port] = counter.get()
            }
        }
    }
}

class Counter(private var v: Long = 0L) {
    fun inc() { v++ }

    fun add(x: Long) { v += x }

    fun get() = v
}


fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    val puzzle = Day10(file)
    puzzle.solve()
    puzzle.solvePart2()
}