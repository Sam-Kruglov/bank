package com.samkruglov.bank.api.graphql

import com.samkruglov.bank.domain.core.shared.CreatePersonCommand
import com.samkruglov.bank.domain.core.shared.SetPersonAddressCommand
import graphql.kickstart.tools.GraphQLMutationResolver
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service

@Service
class Mutation(val commandGateway: CommandGateway) : GraphQLMutationResolver {

    fun createPerson(
            firstName: String,
            lastName: String,
            email: String,
            phone: String
    ) = commandGateway.send<String>(CreatePersonCommand(firstName, lastName, email, phone))

    fun setPersonAddress(
            personId: String,
            apartmentNumber: String?,
            houseNumber: String,
            street: String,
            city: String,
            countryCode: String
    ) = commandGateway.send<Void>(SetPersonAddressCommand(
            personId,
            apartmentNumber,
            houseNumber,
            street,
            city,
            countryCode
    )).thenApply { true }
}