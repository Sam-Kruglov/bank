package com.samkruglov.bank.ui

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UIApplication

fun main(args: Array<String>) {
    runApplication<UIApplication>(*args)
}
