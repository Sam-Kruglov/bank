package com.samkruglov.bank.service;

import com.samkruglov.bank.domain.Card;

import java.time.LocalDate;
import java.util.Optional;

public interface CardService {

    Optional<Card> get(Long id);

    Card create(Long accountId, LocalDate expiryDate, LocalDate issueDate, String number);

    boolean remove(Long id);
}
