package com.samkruglov.bank.dao.jpa;

import com.samkruglov.bank.dao.interfaces.UserRepo;
import com.samkruglov.bank.dao.jpa.common.MutableRepoWithCustomDelete;
import com.samkruglov.bank.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepoJpa extends UserRepo, MutableRepoWithCustomDelete<User, Long>,
        org.springframework.data.repository.Repository<User, Long> {

    @EntityGraph(attributePaths = "accounts")
    @Override
    Optional<User> findWithAccountsById(Long id);
}
