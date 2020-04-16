package com.samkruglov.bank.api.graphql

import com.samkruglov.bank.api.graphql.config.ApiMapper
import com.samkruglov.bank.domain.read.service.PersonReadService
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Service

//todo have to return future: check up on https://github.com/graphql-java-kickstart/graphql-spring-boot/issues/326
@Service
class Query(
        val readService: PersonReadService,
        val mapper: ApiMapper
) : GraphQLQueryResolver {

    fun person(id: String) = readService.findById(id).map { mapper.toType(it) }.toFuture()
}