package com.ionutciuta.challenges

import com.ionutciuta.tools.Challenge
import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools
import kotlin.math.absoluteValue

class Day12(file: String): Challenge {
    private val data = Input(file).readLines()
    private val moves = readMoves()

    override fun solve() {
        val n = Navigator(moves)
        n.navigate()
        println(n.computeDistance())
    }

    override fun solvePart2() {
        val n = WaypointNavigator(moves, Point(10, 1))
        n.navigateWithWaypoint()
        println(n.computeDistance())
    }

    private fun readMoves(): List<Move> = data.map {
        val direction = it[0]
        val units = it.split(direction)[1].toInt()
        Move(direction, units)
    }
}

data class Move(val direction: Char, val units: Int, var isRotation: Boolean = false) {
    init {
        isRotation = direction == 'R' || direction == 'L'
    }
}

object Compass {
    private val orientations = arrayOf('E', 'N', 'W', 'S')

    private val quadrantDirections = mapOf(
        0 to ( 1 to  1),
        1 to (-1 to  1),
        2 to (-1 to -1),
        3 to ( 1 to -1)
    )

    fun computeQuadrant(currentOrientation: Char, move: Move): Int {
        val quadrantHop = move.units/90
        val orientationQuadrant = orientations.indexOf(currentOrientation)
        val rawQuadrant = when(move.direction) {
            'R' -> orientationQuadrant - quadrantHop
            'L' -> orientationQuadrant + quadrantHop
            else -> orientationQuadrant
        }
        return normaliseQ(rawQuadrant)
    }

    fun normaliseQ(q: Int): Int {
        if(q < 0) return 4 + q
        if(q > 3) return q - 4
        return q
    }

    fun computeOrientation(currentOrientation: Char, move: Move): Char {
        val nextQuadrant = computeQuadrant(currentOrientation, move)
        return computeOrientation(nextQuadrant)
    }

    fun computeOrientation(q: Int) = orientations[q]

    fun getQuadrantDirections(q: Int) = quadrantDirections[q]
}

open class Navigator(val moves: List<Move>, val debug: Boolean = false) {
    protected val ship = Point()
    protected var orientation = 'E'

    fun navigate() {
        moves.forEach {
            show("Before move: $it")
            if(it.isRotation) rotate(it) else move(it.direction, it.units)
            show("After move: $it")
        }
    }

    fun computeDistance() = ship.x.absoluteValue + ship.y.absoluteValue

    private fun move(direction: Char, units: Int) {
        if(direction == 'F') {
            move(orientation, units)
            return
        } else {
            when(direction) {
                'N' -> ship.y += units
                'W' -> ship.x -= units
                'S' -> ship.y -= units
                'E' -> ship.x += units
            }
        }
    }

    private fun rotate(rotation: Move) {
        orientation = Compass.computeOrientation(orientation, rotation)
    }

    protected open fun show(prompt: String = ">") {
        if(debug) {
            println("$prompt $orientation - ${ship.x}, ${ship.y}")
        }
    }
}

class WaypointNavigator(moves: List<Move>, val waypoint: Point): Navigator(moves, true) {

    fun navigateWithWaypoint() {
        moves.forEach {
            show("Before move: $it")
            if(it.direction == 'F') {
                moveShip(it.units)
            } else {
                if(it.isRotation) rotateWaypoint(it) else moveWaypoint(it)
            }
            show("After move: $it")
        }
    }

    private fun moveShip(units: Int) {
        ship.x += units * waypoint.x
        ship.y += units * waypoint.y
    }

    private fun moveWaypoint(move: Move) {
        when(move.direction) {
            'N' -> waypoint.y += move.units
            'W' -> waypoint.x -= move.units
            'S' -> waypoint.y -= move.units
            'E' -> waypoint.x += move.units
        }
    }

    private fun rotateWaypoint(rotation: Move) {
        val nextQuadrant = Compass.computeQuadrant(orientation, rotation)
        orientation = Compass.computeOrientation(nextQuadrant)
        val qDir = Compass.getQuadrantDirections(nextQuadrant)!!
        waypoint.x *= qDir.first
        waypoint.y *= qDir.second
    }

    override fun show(prompt: String) {
        if(debug) {
            println("$prompt Waypoint - ${waypoint.x}, ${waypoint.y}")
            println("$prompt $orientation - ${ship.x}, ${ship.y}")
            println("----")
        }
    }
}

data class Point(var x: Int = 0, var y: Int = 0)

fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    val puzzle = Day12(file)
    puzzle.solve()
    puzzle.solvePart2()
}