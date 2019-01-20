package com.samkruglov.bank.service.impl;

import com.samkruglov.bank.dao.interfaces.AccountRepo;
import com.samkruglov.bank.dao.interfaces.UserRepo;
import com.samkruglov.bank.domain.Account;
import com.samkruglov.bank.domain.User;
import com.samkruglov.bank.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static com.samkruglov.bank.dao.interfaces.common.ImmutableRepo.unwrapped;

@AllArgsConstructor
@Service
class AccountServiceImpl implements AccountService {

    private final AccountRepo repo;
    private final UserRepo userRepo;
    private final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

    @Override
    public Optional<Account> get(@NonNull Long id) {
        return repo.findById(id);
    }

    @Override
    public Optional<Account> getWithCards(@NonNull Long id) {
        return repo.findWithCardsById(id);
    }

    @Override
    @Transactional
    public Account create(@NonNull Long userId, Account.Type type) {
        User user = unwrapped(userRepo::findById, userId);
        return repo.save(new Account(user, String.valueOf(threadLocalRandom.nextInt()), type));
    }

    @Override
    public boolean remove(@NonNull Long id) {
        return repo.deleteById(id);
    }
}
