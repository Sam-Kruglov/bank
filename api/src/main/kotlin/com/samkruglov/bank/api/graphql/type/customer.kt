package com.samkruglov.bank.api.graphql.type

import com.github.pozo.KotlinBuilder

@KotlinBuilder
data class Person(
        val id: String,
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

