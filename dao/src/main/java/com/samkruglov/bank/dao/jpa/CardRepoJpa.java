package com.samkruglov.bank.dao.jpa;

import com.samkruglov.bank.dao.interfaces.CardRepo;
import com.samkruglov.bank.domain.Card;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepoJpa extends CardRepo, org.springframework.data.repository.Repository<Card, Long> {
}
