package io.antoniomayk.coses

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CosesApplication

fun main(args: Array<String>) {
    runApplication<CosesApplication>(*args)
}
