package com.samkruglov.bank.domain.read.config

import com.samkruglov.bank.domain.core.shared.PersonAddressSetEvent
import com.samkruglov.bank.domain.core.shared.PersonCreatedEvent
import com.samkruglov.bank.domain.read.Address
import com.samkruglov.bank.domain.read.Person
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget

@Mapper
internal interface ReadMapper {

    @Mapping(target = "address", ignore = true)
    fun toEntity(event: PersonCreatedEvent): Person

    fun toAddress(event: PersonAddressSetEvent): Address

    fun updateState(event: PersonAddressSetEvent, @MappingTarget address: Address)
}