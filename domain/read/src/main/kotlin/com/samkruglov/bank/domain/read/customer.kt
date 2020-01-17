package com.samkruglov.bank.domain.read

import com.github.pozo.KotlinBuilder
import org.springframework.data.annotation.Id

@KotlinBuilder
data class Person(
        @Id val id: String,
        val firstName: String,
        val lastName: String,
        val email: String,
        val phone: String,
        var address: Address? = null
)

@KotlinBuilder
data class Address(
        var apartmentNumber: String? = null,
        var houseNumber: String,
        var street: String,
        var city: String,
        var countryCode: String
)