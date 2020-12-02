package com.ionutciuta.tools

import java.io.File
import kotlin.system.exitProcess


class Input(filename: String) {
    var input: File

    init {
        println("Reading from $filename")
        this.input = File(filename)
    }

    fun readIntSet(): Set<Int> = input.readLines().map { it.toInt() }.toSet()

    fun readLines(): List<String> = input.readLines()
}

object Tools {
    fun getInput(args: Array<String>): String {
        if(args.size != 1) {
            exitProcess(1)
        }

        val input = args[0]
        println("Using as input: $input\n")

        return input
    }
}