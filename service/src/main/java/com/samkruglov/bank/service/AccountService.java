package com.samkruglov.bank.service;

import com.samkruglov.bank.domain.Account;

import java.util.Optional;

public interface AccountService {

    Optional<Account> get(Long id);

    Optional<Account> getWithCards(Long id);

    Account create(Long userId, Account.Type type);

    boolean remove(Long id);
}
