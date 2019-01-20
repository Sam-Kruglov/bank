package com.samkruglov.bank.dao.interfaces;

import com.samkruglov.bank.dao.interfaces.common.Repo;
import com.samkruglov.bank.domain.User;

import java.util.Optional;

public interface UserRepo extends Repo<User, Long> {
    Optional<User> findWithAccountsById(Long id);
}
