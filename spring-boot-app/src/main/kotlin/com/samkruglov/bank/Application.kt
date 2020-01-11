package com.samkruglov.bank

import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

    @Bean
    fun inMemoryEventStore() = InMemoryEventStorageEngine()
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}