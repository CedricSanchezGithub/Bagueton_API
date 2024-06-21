package org.bagueton_api

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class BaguetonApiApplication{

    @Bean
    fun init() = ApplicationRunner {
        println("Mise à jour correctement effectuée 21/06/2024")
    }
}

fun main(args: Array<String>) {
    runApplication<BaguetonApiApplication>(*args)
}
