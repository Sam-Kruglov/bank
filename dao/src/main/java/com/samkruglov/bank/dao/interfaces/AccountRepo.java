package com.samkruglov.bank.dao.interfaces;

import com.samkruglov.bank.dao.interfaces.common.Repo;
import com.samkruglov.bank.domain.Account;

import java.util.Optional;

public interface AccountRepo extends Repo<Account, Long> {

    Optional<Account> findWithCardsById(Long id);
}
