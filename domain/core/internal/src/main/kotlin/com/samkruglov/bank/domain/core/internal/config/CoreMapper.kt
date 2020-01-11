package com.samkruglov.bank.domain.core.internal.config

import com.samkruglov.bank.domain.core.internal.Address
import com.samkruglov.bank.domain.core.internal.Person
import com.samkruglov.bank.domain.core.shared.CreatePersonCommand
import com.samkruglov.bank.domain.core.shared.PersonAddressSetEvent
import com.samkruglov.bank.domain.core.shared.PersonCreatedEvent
import com.samkruglov.bank.domain.core.shared.SetPersonAddressCommand
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget

@Mapper
interface CoreMapper {

    @Mapping(target = "address", ignore = true)
    fun updateState(event: PersonCreatedEvent, @MappingTarget person: Person)

    fun toAddress(event: PersonAddressSetEvent): Address

    fun updateState(event: PersonAddressSetEvent, @MappingTarget address: Address)

    fun toEvent(id: String, command: CreatePersonCommand): PersonCreatedEvent

    fun toEvent(command: SetPersonAddressCommand): PersonAddressSetEvent
}