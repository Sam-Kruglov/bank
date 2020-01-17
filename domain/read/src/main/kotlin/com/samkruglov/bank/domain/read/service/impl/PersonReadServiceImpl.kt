package com.samkruglov.bank.domain.read.service.impl

import com.samkruglov.bank.domain.read.repository.PersonRepo
import com.samkruglov.bank.domain.read.service.PersonReadService
import org.springframework.stereotype.Service

@Service
internal class PersonReadServiceImpl(val repo: PersonRepo) : PersonReadService {
    override fun findById(id: String) = repo.findById(id)
    override fun findAll() = repo.findAll()
}