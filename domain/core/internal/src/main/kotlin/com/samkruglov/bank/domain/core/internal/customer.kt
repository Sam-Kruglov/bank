package com.samkruglov.bank.domain.core.internal

import com.github.pozo.KotlinBuilder
import com.samkruglov.bank.domain.core.internal.config.CoreMapper
import com.samkruglov.bank.domain.core.shared.CreatePersonCommand
import com.samkruglov.bank.domain.core.shared.PersonAddressSetEvent
import com.samkruglov.bank.domain.core.shared.PersonCreatedEvent
import com.samkruglov.bank.domain.core.shared.SetPersonAddressCommand
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.modelling.command.AggregateMember
import org.axonframework.spring.stereotype.Aggregate
import java.util.*

@Aggregate
class Person {
    @AggregateIdentifier lateinit var id: String
    lateinit var firstName: String
    lateinit var lastName: String
    lateinit var email: String
    lateinit var phone: String
    @AggregateMember var address: Address? = null

    @Suppress("ConvertSecondaryConstructorToPrimary")
    @CommandHandler
    constructor(command: CreatePersonCommand, mapper: CoreMapper) {
        AggregateLifecycle.apply(mapper.toEvent(UUID.randomUUID().toString(), command))
    }

    @EventSourcingHandler
    fun on(event: PersonCreatedEvent, mapper: CoreMapper) {
        mapper.updateState(event, this)
    }

    @CommandHandler
    fun on(command: SetPersonAddressCommand, mapper: CoreMapper) {
        AggregateLifecycle.apply(mapper.toEvent(command))
    }

    @EventSourcingHandler
    fun on(event: PersonAddressSetEvent, mapper: CoreMapper) {
        if (address == null) {
            address = mapper.toAddress(event)
            return
        }
        address!!.on(event, mapper)
    }
}

@Aggregate
@KotlinBuilder
data class Address(
        var apartmentNumber: String?,
        var houseNumber: String,
        var street: String,
        var city: String,
        var countryCode: String
) {

    fun on(event: PersonAddressSetEvent, mapper: CoreMapper) {
        mapper.updateState(event, this)
    }
}