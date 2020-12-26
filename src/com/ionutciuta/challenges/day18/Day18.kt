package com.ionutciuta.challenges.day18

import com.ionutciuta.tools.Challenge
import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools
import java.lang.UnsupportedOperationException
import java.util.*

typealias Operation = (String, String) -> Long

class Day18(file: String): Challenge {
    private val data = cleanData(file)
    var opMultiply: Operation = { p1: String, p2: String -> p1.toLong() * p2.toLong() }
    var opAddition: Operation = { p1: String, p2: String -> p1.toLong() + p2.toLong() }

    private fun String.isMul(): Boolean = this == "*"
    private fun String.isAdd(): Boolean = this == "+"
    private fun String.isOp(): Boolean = this.isAdd() || this.isMul()
    private fun String.isOpen(): Boolean = this == "("
    private fun String.isClose(): Boolean = this == ")"
    private fun String.toOp(): Operation {
        return when {
            this.isMul() -> opMultiply
            this.isAdd() -> opAddition
            else -> throw UnsupportedOperationException("$this is not a valid operation")
        }
    }

    override fun solve() {
        var sum = 0L

        for (line in data) {
            sum += evaluate(line)
        }
        println(sum)
    }

    private fun evaluate(line: List<String>): Long {
        val params = Stack<String>()
        val ops = Stack<Operation>()

        for (e in line) {
//            println("e: $e")
//            println("params: $params")
//            println("ops: $ops")
            when {
                e.isOp() -> ops.push(e.toOp())
                e.isOpen() -> params.push(e)
                e.isClose() -> eagerCleanup(params, ops)
                else -> eagerEval(e, params, ops)
            }
//            println()
        }

        return params.pop().toLong()
    }

    private fun eagerEval(e: String, params: Stack<String>, ops: Stack<Operation>) {
        if(params.isEmpty() || params.peek().isOpen()) {
            params.push(e)
        } else {
            val op = ops.pop()
            val top = params.pop()
            val newTop = op(top, e).toString()
            params.push(newTop)
        }
    }

    private fun eagerCleanup(params: Stack<String>, ops: Stack<Operation>) {
        val top = params.pop()
        val next = params.pop()
        if(!next.isOpen()) {
            throw UnsupportedOperationException("Trying to cleanup $next. Oops. Top was $top\n")
        }
        eagerEval(top, params, ops)
//        println("Params post clean: $params")
    }

    private fun lazyEval(params: Stack<String>, ops: Stack<Operation>) {
        if(params.size == 1 && ops.size == 0) {
            return
        }

        val head = params.pop()
        val next = params.pop()

        if(next.isOpen()) {
            params.push(head)
            return
        }

        val op = ops.pop()
        params.push(op(head, next).toString())
        lazyEval(params, ops)
    }

    override fun solvePart2() {

    }

    private fun cleanData(file: String): List<List<String>> {
        return Input(file).readLines().map {
            it.toCharArray().filter { it != ' ' }.map { it.toString() }.toList()
        }.toList()
    }
}

fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    Day18(file).solve()
    Day18(file).solvePart2()
}