package com.samkruglov.bank.api.graphql.config

import com.samkruglov.bank.api.graphql.type.Person
import org.mapstruct.Mapper

@Mapper
interface ApiMapper {
    fun toType(person: com.samkruglov.bank.domain.read.Person): Person

}
