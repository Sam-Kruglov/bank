package com.samkruglov.bank.domain.read.service

import com.samkruglov.bank.domain.core.shared.PersonAddressSetEvent
import com.samkruglov.bank.domain.core.shared.PersonCreatedEvent
import com.samkruglov.bank.domain.read.config.ReadMapper
import com.samkruglov.bank.domain.read.repository.PersonRepo
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Service

@Service
internal class PersonBuildService(
        val repo: PersonRepo,
        val mapper: ReadMapper
) {

    @EventHandler
    fun on(event: PersonCreatedEvent) {
        repo.save(mapper.toEntity(event)).block()
    }

    @EventHandler
    fun on(event: PersonAddressSetEvent) {
        repo.findById(event.personId)
                .doOnNext {
                    if (it.address == null) {
                        it.address = mapper.toAddress(event)
                    } else {
                        mapper.updateState(event, it.address!!)
                    }
                }
                .flatMap { repo.save(it) }
                .block()
    }
}