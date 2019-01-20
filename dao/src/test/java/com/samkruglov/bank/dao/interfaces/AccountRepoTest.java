package com.samkruglov.bank.dao.interfaces;

import com.samkruglov.bank.dao.jpa.JpaTest;
import com.samkruglov.bank.domain.Account;
import com.samkruglov.bank.domain.Card;
import com.samkruglov.bank.domain.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@JpaTest
public class AccountRepoTest {

    final AccountRepo repo;
    final UserRepo userRepo;
    final CardRepo cardRepo;

    @DisplayName("user exists -- findById does not find")
    @Test
    void findById_userExistsAccountDoesNotExist_notFound() {

        //arrange
        User existingUser = userRepo.save(new User("John", "Smith"));
        long accountId = 1L;

        //act & assert
        assertThat(repo.findById(accountId)).isEmpty();
    }

    @DisplayName("user exists -- findWithCardsById does not find")
    @Test
    void findWithCardsById_userExistsAccountDoesNotExist_notFound() {

        //arrange
        User existingUser = userRepo.save(new User("John", "Smith"));
        long accountId = 1L;

        //act & assert
        assertThat(repo.findWithCardsById(accountId)).isEmpty();
    }

    @DisplayName("user exists -- deleteById returns false")
    @Test
    void deleteById_userExistsAccountDoesNotExist_false() {

        //arrange
        User existingUser = userRepo.save(new User("John", "Smith"));
        long accountId = 1L;

        //act & assert
        assertThat(repo.deleteById(accountId)).isFalse();
    }

    @DisplayName("user exists -- save persists")
    @Test
    void save_userExistsAccountDoesNotExist_persisted() {

        //arrange
        User existingUser = userRepo.save(new User("John", "Smith"));
        Account account = new Account(existingUser, "1", null);

        //act
        repo.save(account);

        //assert
        assertThat(repo.findById(account.getId())).contains(account);
    }

    @DisplayName("user exists -- save persists cards")
    @Test
    void save_userExistsAccountDoesNotExist_cardsPersisted() {

        //arrange
        User existingUser = userRepo.save(new User("John", "Smith"));
        Account account = new Account(existingUser, "1", null);
        Card card = new Card(LocalDate.now(), null, null);
        account.addCard(card);

        //act
        repo.save(account);

        //assert
        assertThat(cardRepo.findById(card.getId())).contains(card);
    }

    @DisplayName("user exists -- account exists -- save persists")
    @Test
    void save_userExistsAccountExists_persisted() {

        //arrange
        User existingUser = userRepo.save(new User("John", "Smith"));
        Account existingAccount = repo.save(new Account(existingUser, "1", null));

        //act
        Account newAccount = repo.save(new Account(existingUser, "1", null));

        //assert
        assertThat(repo.findById(newAccount.getId())).contains(newAccount);
    }

    @DisplayName("user exists -- account exists -- card exists -- save removes cards deleted from set")
    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    void save_editExistingAccount_changed() {

        //arrange
        User existingUser = userRepo.save(new User("John", "Smith"));
        Account existingAccount = new Account(existingUser, "1", null);
        Card existingCard = new Card(LocalDate.now(), null, null);
        existingAccount.addCard(existingCard);
        Long id = repo.save(existingAccount).getId();

        TestTransaction.flagForCommit();
        TestTransaction.end();

        //act
        TestTransaction.start();

        //remove card from the set
        Account originalExistingAccount = repo.findWithCardsById(id).get();
        originalExistingAccount.removeCard(existingCard);

        TestTransaction.flagForCommit();
        TestTransaction.end();

        //assert
        assertThat(cardRepo.findById(existingCard.getId())).isEmpty();
    }

    @DisplayName("user exists -- account exists -- findById finds")
    @Test
    void findById_userExistsAccountExists_found() {

        //arrange
        User existingUser = userRepo.save(new User("John", "Smith"));
        Account existingAccount = repo.save(new Account(existingUser, "1", null));

        //act & assert
        assertThat(repo.findById(existingAccount.getId())).contains(existingAccount);
    }

    @DisplayName("user exists -- account exists -- findWithCardsById finds ")
    @Test
    void findWithCardsById_userExistsAccountExists_found() {

        //arrange
        User existingUser = userRepo.save(new User("John", "Smith"));
        Account existingAccount = repo.save(new Account(existingUser, "1", null));

        //act & assert
        assertThat(repo.findWithCardsById(existingAccount.getId())).contains(existingAccount);
    }

    @DisplayName("user exists -- account exists -- card exists -- findWithCardsById finds with cards preloaded")
    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    void findWithCardsById_userExistsAccountExistsCardExists_cardsInitialized() {

        //arrange
        User existingUser = userRepo.save(new User("John", "Smith"));
        Account existingAccount = new Account(existingUser, "1", null);
        Card existingCard = new Card(LocalDate.now(), null, null);
        existingAccount.addCard(existingCard);
        repo.save(existingAccount);

        TestTransaction.flagForCommit();
        TestTransaction.end();

        //act
        Optional<Account> foundAccount = repo.findWithCardsById(existingAccount.getId());

        //assert
        assertAll(
                () -> assertThat(foundAccount).contains(existingAccount),
                () -> assertThatCode(() -> foundAccount.get().getReadOnlyCards().size()).doesNotThrowAnyException()
        );
    }

    @DisplayName("user exists -- account exists -- deleteById returns true and removes")
    @Test
    void deleteById_userExistsAccountExists_true() {

        //arrange
        User existingUser = userRepo.save(new User("John", "Smith"));
        Account existingAccount = repo.save(new Account(existingUser, "1", null));

        //act
        boolean removed = repo.deleteById(existingAccount.getId());

        //assert
        assertAll(
                () -> assertThat(removed).isTrue(),
                () -> assertThat(repo.findById(existingAccount.getId())).isEmpty()
        );
    }

    @DisplayName("user exists -- account exists -- card exists -- deleteById removes cards")
    @Test
    void deleteById_userExistsAccountExistsCardExists_cardsRemoved() {

        //arrange
        User existingUser = userRepo.save(new User("John", "Smith"));
        Account existingAccount = new Account(existingUser, "1", null);
        Card existingCard = new Card(LocalDate.now(), null, null);
        existingAccount.addCard(existingCard);
        repo.save(existingAccount);

        //act
        boolean removed = repo.deleteById(existingAccount.getId());

        //assert
        assertThat(cardRepo.findById(existingCard.getId())).isEmpty();
    }
}