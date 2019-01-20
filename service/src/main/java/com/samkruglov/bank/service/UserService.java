package com.samkruglov.bank.service;

import com.samkruglov.bank.domain.User;

import java.util.Optional;

public interface UserService {

    Optional<User> get(Long id);

    Optional<User> getWithAccounts(Long id);

    User create(String firstName, String lastName);

    boolean remove(Long id);
}
