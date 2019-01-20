package com.samkruglov.bank.service.impl;

import com.samkruglov.bank.dao.interfaces.UserRepo;
import com.samkruglov.bank.domain.User;
import com.samkruglov.bank.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
class UserServiceImpl implements UserService {

    private final UserRepo repo;

    @Override
    public Optional<User> get(@NonNull Long id) {
        return repo.findById(id);
    }

    @Override
    public Optional<User> getWithAccounts(@NonNull Long id) {
        return repo.findWithAccountsById(id);
    }

    @Override
    public User create(String firstName, String lastName) {
        return repo.save(new User(firstName, lastName));
    }

    @Override
    public boolean remove(@NonNull Long id) {
        return repo.deleteById(id);
    }
}
