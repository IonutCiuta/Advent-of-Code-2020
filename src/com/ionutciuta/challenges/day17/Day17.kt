package com.ionutciuta.challenges.day17

import com.ionutciuta.tools.Challenge
import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools

class Day17(file: String, private val cycles: Int): Challenge {
    private val data = Input(file).readLines()
    private val size = data.size
    private val padding = cycles + 1
    private val planeSize = size + 2 * padding

    private fun generateEmptyPlane() = Plane(MutableList(planeSize) { MutableList(planeSize) { 0 } })

    private fun generateCentralPlane(): Plane {
        val input = data.map { it.map { if (it == '#') 1 else 0 }.toList() }.toList()
        val template = generateEmptyPlane()

        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, col ->
                template.set(padding + rowIndex, padding + colIndex, col)
            }
        }

        return template
    }

    private fun generateSpace(centralPlane: Plane, cycles: Int): Space {
        val planes = HashMap<Int, Plane>()
        planes[0] = centralPlane
        for (i in 1..cycles) {
            planes[-i] = generateEmptyPlane()
            planes[i] = generateEmptyPlane()
        }
        return Space(planes)
    }

    private fun showPlane(plane: MutableList<MutableList<Int>>) {
        println("${plane.size} ${plane[0].size}")
    }

    override fun solve() {
        val centralPlane = generateCentralPlane()
        println(centralPlane.count1s())
        val space = generateSpace(centralPlane, cycles)
        println(space.count1s())
    }

    override fun solvePart2() {
    }
}

class Space(private val planes: HashMap<Int, Plane>) {
    fun point(x: Int, y: Int, z: Int) = planes[z]?.point(x, y) ?: 0
    fun count1s() = planes.map { it.value.count1s() }.sum()
}

class Plane(private val points: MutableList<MutableList<Int>>) {
    fun point(x: Int, y: Int) = points[x][y]
    fun set(x: Int, y: Int, value: Int) { points[x][y] = value }
    fun show() { points.forEach { it.forEach { print(it) }; println() } }
    fun count1s() = points.map { it.sum() }.sum()
}

fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    val puzzle = Day17(file, 6)
    puzzle.solve()
    puzzle.solvePart2()
}