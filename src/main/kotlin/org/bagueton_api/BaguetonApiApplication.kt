package org.bagueton_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BaguetonApiApplication

fun main(args: Array<String>) {
    runApplication<BaguetonApiApplication>(*args)
}
