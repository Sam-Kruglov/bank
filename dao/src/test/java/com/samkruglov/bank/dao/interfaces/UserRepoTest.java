package com.samkruglov.bank.dao.interfaces;

import com.samkruglov.bank.dao.jpa.JpaTest;
import com.samkruglov.bank.domain.Account;
import com.samkruglov.bank.domain.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@JpaTest
public class UserRepoTest {

    final UserRepo repo;
    final AccountRepo accountRepo;

    @DisplayName("findById does not find")
    @Test
    void findById_userDoesNotExist_notFound() {

        //arrange
        long userId = 1L;

        //act & assert
        assertThat(repo.findById(1L)).isEmpty();
    }

    @DisplayName("findWithAccountsById does not find")
    @Test
    void findWithAccountsById_userDoesNotExist_notFound() {

        //arrange
        long userId = 1L;

        //act & assert
        assertThat(repo.findWithAccountsById(1L)).isEmpty();
    }

    @DisplayName("save persists")
    @Test
    void save_userDoesNotExist_persisted() {

        //arrange
        User newUser = new User("John", "Smith");

        //act
        User savedUser = repo.save(newUser);

        //assert
        assertThat(repo.findById(savedUser.getId())).contains(savedUser);
    }

    @DisplayName("deleteById returns false")
    @Test
    void deleteById_userDoesNotExist_false() {

        //arrange
        long id = 1L;

        //act & assert
        assertThat(repo.deleteById(id)).isFalse();
    }

    @DisplayName("user exists -- findById finds")
    @Test
    void findById_userExists_found() {

        //arrange
        User existingUser = repo.save(new User("John", "Smith"));

        //act & assert
        assertThat(repo.findById(existingUser.getId())).contains(existingUser);
    }

    @DisplayName("user exists -- findWithAccountsById finds")
    @Test
    void findWithAccountsById_userExists_found() {

        //arrange
        User existingUser = repo.save(new User("John", "Smith"));

        //act & assert
        assertThat(repo.findWithAccountsById(existingUser.getId())).contains(existingUser);
    }

    @DisplayName("user exists -- save updates edits")
    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    void save_editExistingUser_changed() {

        //arrange
        Long id = repo.save(new User("John", "Smith")).getId();
        TestTransaction.flagForCommit();
        TestTransaction.end();

        //act
        TestTransaction.start();

        //change first name
        User originalExistingUser = repo.findById(id).get();
        String originalFirstName = originalExistingUser.getFirstName();
        originalExistingUser.setFirstName(originalFirstName.toUpperCase());

        TestTransaction.flagForCommit();
        TestTransaction.end();

        //assert
        assertThat(repo.findById(id).get().getFirstName()).isNotEqualTo(originalFirstName);
    }

    @DisplayName("user exists -- save persists")
    @Test
    void save_userExists_persisted() {

        //arrange
        User existingUser = repo.save(new User("John", "Smith"));
        User newUser = new User("John", "Smith");

        //act
        User savedUser = repo.save(newUser);

        //assert
        assertThat(repo.findById(savedUser.getId())).contains(savedUser);
    }

    @DisplayName("user exists -- deleteById returns true and removes")
    @Test
    void deleteById_userExists_trueAndUserRemoved() {

        //arrange
        User existingUser = repo.save(new User("John", "Smith"));
        long id = existingUser.getId();

        //act
        boolean removed = repo.deleteById(id);

        //assert
        assertAll(
                () -> assertThat(removed).isTrue(),
                () -> assertThat(repo.findById(id)).isEmpty()
        );
    }

    @DisplayName("user exists -- account exists -- deleteById removes account")
    @Test
    void deleteById_userExistsAccountExists_accountRemoved() {

        //arrange
        User existingUser = repo.save(new User("John", "Smith"));
        Account existingAccount = accountRepo.save(new Account(existingUser, "1", null));

        //act
        repo.deleteById(existingUser.getId());

        //assert
        assertThat(accountRepo.findById(existingAccount.getId())).isEmpty();
    }

    @DisplayName("user exists -- account exists -- findWithAccountsById finds with accounts preloaded")
    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    void findWithAccountsById_userExistsAccountExists_accountsInitialized() {

        //arrange
        User existingUser = repo.save(new User("John", "Smith"));
        Account existingAccount = accountRepo.save(new Account(existingUser, "1", null));

        TestTransaction.flagForCommit();
        TestTransaction.end();

        //act
        Optional<User> foundUser = repo.findWithAccountsById(existingUser.getId());

        //assert
        assertAll(
                () -> assertThat(foundUser).contains(existingUser),
                () -> assertThatCode(() -> foundUser.get().getReadOnlyAccounts().size()).doesNotThrowAnyException()
        );
    }
}