package com.samkruglov.bank.domain.core.shared

import com.github.pozo.KotlinBuilder
import org.axonframework.modelling.command.TargetAggregateIdentifier

//todo public messages and private messages should exist, may differ, and public are backwards-compatible

@KotlinBuilder
data class CreatePersonCommand(
        val firstName: String,
        val lastName: String,
        val email: String,
        val phone: String
)

@KotlinBuilder
data class PersonCreatedEvent(
        val id: String,
        val firstName: String,
        val lastName: String,
        val email: String,
        val phone: String
)

// can we store deltas instead of the whole address? using untyped json to differ "not changed" and "set to null"
@KotlinBuilder
data class PersonAddressSetEvent(
        val personId: String,
        val apartmentNumber: String?,
        val houseNumber: String,
        val street: String,
        val city: String,
        val countryCode: String
)

@KotlinBuilder
data class SetPersonAddressCommand(
        @TargetAggregateIdentifier
        val personId: String,
        val apartmentNumber: String?,
        val houseNumber: String,
        val street: String,
        val city: String,
        val countryCode: String
)