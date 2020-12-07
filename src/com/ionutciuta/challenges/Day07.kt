package com.ionutciuta.challenges

import com.ionutciuta.tools.Challenge
import com.ionutciuta.tools.Input
import com.ionutciuta.tools.Tools
import java.util.concurrent.atomic.AtomicInteger

class Day07(file: String): Challenge {
    private val data = Input(file).readLines()

    override fun solve() {
        val bags = data.map { getBagFromDescription(it) }.map { it.color to it }.toMap()
        bags.forEach {
            println(it)
        }

        println()

        val target = "shiny gold"
        var counter = AtomicInteger(0)
        for (bag in bags) {
            recursiveCheck(bags[bag.key]!!, bags, target, counter)
            println()
        }
        println(counter.get())
    }

    fun recursiveCheck(bag: Bag, allBags: Map<String, Bag>, targetBag: String, counter: AtomicInteger): Boolean {
        println("Looking into ${bag.color}")
        if (bag.bags.containsKey(targetBag)) {
            println("${bag.color} contains $targetBag!")
            counter.getAndIncrement()
            return true
        }

        bag.bags.forEach {
            println("Looking into ${bag.color} -> ${it.key}")
            return recursiveCheck(allBags[it.key]!!, allBags, targetBag, counter)
        }

        return false
    }

    override fun solvePart2() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getBagFromDescription(input: String): Bag {
        val def = input.split("contain")

        val bagColor = def[0].trim().split("bags")[0].trim()
        val bag = Bag(bagColor)

        val content = def[1].trim()
        if(content != "no other bags.") {
            val containedBags = content.split(",")
            // println("This bag is complex: $content. Contains ${containedBags.size} other bags.")
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

data class Bag(val color: String,
               val bags: MutableMap<String, Int> = mutableMapOf()) {
    fun addBag(color: String, count: Int) {
        bags[color] = count
    }
}

fun main(args: Array<String>) {
    val file = Tools.getInput(args)
    Day07(file).solve()
    Day07(file).solvePart2()
}


