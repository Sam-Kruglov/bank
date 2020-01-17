package com.samkruglov.bank.domain.read.repository

import com.samkruglov.bank.domain.read.Person
import org.springframework.data.repository.reactive.ReactiveCrudRepository

internal interface PersonRepo : ReactiveCrudRepository<Person, String>