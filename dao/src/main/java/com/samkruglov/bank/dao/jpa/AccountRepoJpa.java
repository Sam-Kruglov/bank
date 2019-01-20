package com.samkruglov.bank.dao.jpa;

import com.samkruglov.bank.dao.interfaces.AccountRepo;
import com.samkruglov.bank.dao.jpa.common.MutableRepoWithCustomDelete;
import com.samkruglov.bank.domain.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepoJpa extends AccountRepo, MutableRepoWithCustomDelete<Account, Long>,
        org.springframework.data.repository.Repository<Account, Long> {

    @EntityGraph(attributePaths = "cards")
    @Override
    Optional<Account> findWithCardsById(Long id);
}
