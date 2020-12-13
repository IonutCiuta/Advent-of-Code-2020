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
    private val values = arrayOf('E', 'N', 'W', 'S')

    fun computeOrientation(currentOrientation: Char, move: Move): Char {
        val quadrantHop = move.units/90
        val q = values.indexOf(currentOrientation)
        val nextQuadrant = when(move.direction) {
            'R' -> q - quadrantHop
            'L' -> q + quadrantHop
            else -> q
        }
        return computeOrientation(nextQuadrant)
    }

    private fun computeOrientation(q: Int): Char {
        if(q < 0)
            return values[4 + q]
        if(q > 3)
            return values[q - 4]
        return return values[q]
    }
}

class Navigator(val moves: List<Move>) {
    private val ship = Point()
    private var orientation = 'E'

    fun navigate() {
        moves.forEach {
            show("Before move: $it")
            if(it.isRotation) rotate(it) else move(it.direction, it.units)
            show("After move: $it")
            println()
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

    private fun show(prompt: String = ">") {
        println("$prompt $orientation - $ship.x, $ship.y")
    }
}

//class WaypointNavigator(val moves: List<Move>, val waypoint: Point) {
//    private val ship = Point(0,0)
//
//    private fun moveShip(units: Int) {
//        ship.x += units*waypoint.x
//        ship.y += units*waypoint.y
//    }
//
//    private fun moveWaypoint(move: Move) {
//        when(move.direction) {
//            'N' -> waypoint.y += move.units
//            'W' -> waypoint.x -= move.units
//            'S' -> waypoint.y -= move.units
//            'E' -> waypoint.x += move.units
//        }
//    }
//
//    private fun rotateWaypoint() {
//
//    }
//}

data class Point(var x: Int = 0, var y: Int = 0)

fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    val puzzle = Day12(file)
    puzzle.solve()
    puzzle.solvePart2()
}