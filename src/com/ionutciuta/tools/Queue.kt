package com.ionutciuta.tools

data class Queue(val size: Int) {
    var content = mutableListOf<Int>()

    fun add(e: Int) {
        content.add(e)
        if(content.size > size) {
            content = content.subList(1, content.size)
        }
    }

    fun contentSize() = content.size

    override fun toString() = content.toString()
}

fun main() {
    val testQueue = Queue(3)
    testQueue.add(1)
    testQueue.add(2)
    testQueue.add(3)
    testQueue.add(4)
    println(testQueue)
}