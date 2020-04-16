package com.samkruglov.bank.api.graphql

import com.samkruglov.bank.api.graphql.config.ApiMapper
import com.samkruglov.bank.api.graphql.type.Person
import com.samkruglov.bank.domain.read.service.PersonReadService
import graphql.kickstart.tools.GraphQLSubscriptionResolver
import org.reactivestreams.Publisher
import org.springframework.stereotype.Service

//todo have to put all list queries here:
// check up on https://github.com/graphql-java-kickstart/graphql-spring-boot/issues/327
@Service
class Subscription(
        val readService: PersonReadService,
        val mapper: ApiMapper
) : GraphQLSubscriptionResolver {

    fun people(): Publisher<Person> = readService.findAll().map { mapper.toType(it) }
            .log(Subscription::class.qualifiedName!!)
}