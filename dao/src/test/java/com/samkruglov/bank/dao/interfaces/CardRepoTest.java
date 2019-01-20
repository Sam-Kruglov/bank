package com.samkruglov.bank.dao.interfaces;

import com.samkruglov.bank.dao.jpa.JpaTest;
import com.samkruglov.bank.domain.Account;
import com.samkruglov.bank.domain.Card;
import com.samkruglov.bank.domain.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@JpaTest
public class CardRepoTest {

    final UserRepo userRepo;
    final AccountRepo accountRepo;
    final CardRepo repo;

    @DisplayName("account exists -- card does not exist -- findById does not find")
    @Test
    void findById_accountExistsCardDoesNotExist_notFound() {

        //arrange
        User existingUser = userRepo.save(new User("John", "Smith"));
        Account existingAccount = accountRepo.save(new Account(existingUser, "1", null));
        long cardId = 1L;

        //act & assert
        assertThat(repo.findById(cardId)).isEmpty();
    }

    @DisplayName("account exists -- card exists -- findById finds")
    @Test
    void findById_accountExistsCardExists_found() {

        //arrange
        User existingUser = userRepo.save(new User("John", "Smith"));
        Account existingAccount = new Account(existingUser, "1", null);
        Card existingCard = new Card(LocalDate.now(), null, null);
        existingAccount.addCard(existingCard);
        accountRepo.save(existingAccount).getId();

        //act & assert
        assertThat(repo.findById(existingCard.getId())).contains(existingCard);
    }
}