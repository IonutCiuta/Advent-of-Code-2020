package com.ionutciuta.challenges

import com.ionutciuta.tools.Challenge
import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools
import java.util.concurrent.atomic.AtomicInteger

class Day07(file: String): Challenge {
    private val data = Input(file).readLines()
    private val target = "shiny gold"
    private val bags = data.map { getBagFromDescription(it) }.map { it.color to it }.toMap()


    override fun solve() {
        val counter = AtomicInteger(0)
        bags.forEach { recursiveCheck(bags[it.key]!!, bags, target, counter) }
        println(counter.get())
    }

    private fun recursiveCheck(bag: Bag, allBags: Map<String, Bag>, targetBag: String, counter: AtomicInteger): Boolean {
        if (bag.bags.containsKey(targetBag)) {
            counter.incrementAndGet()
            return true
        }

        bag.bags.forEach {
            if(recursiveCheck(allBags[it.key]!!, allBags, targetBag, counter))
                return true
        }

        return false
    }

    override fun solvePart2() {
        val shinyGoldBag = bags[target]!!
        val r = shinyGoldBag.bags.map { it.value * recursiveAdd(bags[it.key]!!, bags) }.sum()
        println(r)
    }

    private fun recursiveAdd(bag: Bag, allBags: Map<String, Bag>): Int {
        var counter = 1
        bag.bags.forEach { counter += it.value * recursiveAdd(allBags[it.key]!!, allBags) }
        return counter
    }

    private fun getBagFromDescription(input: String): Bag {
        val def = input.split("contain")

        val bagColor = def[0].trim().split("bags")[0].trim()
        val bag = Bag(bagColor)

        val content = def[1].trim()
        if(content != "no other bags.") {
            val containedBags = content.split(",")
            containedBags.forEach {
                val otherBag = it.trim().split("bag")[0].trim()
                val otherBagCount = otherBag.substring(0, 1).toInt()
                val otherBagColor = otherBag.substring(1).trim()
                bag.addBag(otherBagColor, otherBagCount)
            }
        }

        return bag
    }
}

data class Bag(val color: String, val bags: MutableMap<String, Int> = mutableMapOf()) {
    fun addBag(color: String, count: Int) {
        bags[color] = count
    }
}

fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    Day07(file).solve()
    Day07(file).solvePart2()
}