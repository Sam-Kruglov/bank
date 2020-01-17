package com.samkruglov.bank.domain.read.service

import com.samkruglov.bank.domain.read.Person
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

//todo any reasonable way to "redirect" GraphQL straight to MongoDB to not select the whole document?
// https://github.com/Soluto/graphql-to-mongodb but it's in NodeJS
interface PersonReadService {

    fun findById(id: String): Mono<Person>

    //todo introduce pagination
    fun findAll(): Flux<Person>
}