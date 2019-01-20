package com.samkruglov.bank.service.impl;

import com.samkruglov.bank.dao.interfaces.AccountRepo;
import com.samkruglov.bank.dao.interfaces.UserRepo;
import com.samkruglov.bank.domain.Account;
import com.samkruglov.bank.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountServiceImplTest {

    final AccountRepo repo = mock(AccountRepo.class);
    final UserRepo userRepo = mock(UserRepo.class);
    final AccountServiceImpl service = new AccountServiceImpl(repo, userRepo);

    @DisplayName("user exists")
    @Nested
    class UserExists {

        final User user;

        UserExists() {
            this.user = mock(User.class);
            long id = 1L;
            when(user.getIdOptional()).thenReturn(Optional.of(id));
            when(user.getId()).thenReturn(id);
            when(userRepo.findById(id)).thenReturn(Optional.of(user));
        }

        @DisplayName("get does not find")
        @Test
        void get_notFound() {

            //arrange
            long accountId = 1L;

            //act & assert
            assertThat(service.get(accountId)).isEmpty();
        }

        @DisplayName("create persists")
        @Test
        void create_persisted() {

            //arrange
            long userId = user.getId();

            //act
            service.create(userId, null);

            //assert
            verify(repo).save(any());
        }

        @DisplayName("remove does not find")
        @Test
        void remove_notFound() {

            //arrange
            long accountId = 1L;

            //act & assert
            assertThat(service.remove(accountId)).isFalse();
        }

        @DisplayName("account exists")
        @Nested
        class AccountExists extends AccountExistsBase {

            @DisplayName("get finds")
            @Test
            void get_found() {

                //arrange
                long accountId = account.getId();

                //act & assert
                assertThat(service.get(accountId)).contains(account);
            }

            @DisplayName("create persists")
            @Test
            void create_persisted() {

                //arrange
                long userId = user.getId();

                //act
                service.create(userId, null);

                //assert
                verify(repo).save(any());
            }

            @DisplayName("remove finds")
            @Test
            void remove_found() {

                //arrange
                long accountId = account.getId();

                //act & assert
                assertThat(service.remove(accountId)).isTrue();
            }
        }
    }

    @DisplayName("user does not exist")
    @Nested
    class UserDoesNotExists {

        @DisplayName("create throws 'not found'")
        @Test
        void create_exception() {

            //arrange
            long userId = 1L;

            //act & assert
            assertThatIllegalArgumentException()
                    .as("cannot create an account for a non existent user")
                    .isThrownBy(() -> service.create(userId, null))
                    .withMessageContaining("Could not find");
        }

        @DisplayName("remove does not find")
        @Test
        void remove_notFound() {

            //arrange
            long accountId = 1L;

            //act & assert
            assertThat(service.remove(accountId)).isFalse();
        }
    }

    class AccountExistsBase {
        final Account account;

        AccountExistsBase() {
            this.account = mock(Account.class);
            long id = 1L;
            when(account.getIdOptional()).thenReturn(Optional.of(id));
            when(account.getId()).thenReturn(id);
            when(repo.findById(id)).thenReturn(Optional.of(account));
            when(repo.findWithCardsById(id)).thenReturn(Optional.of(account));
            when(repo.deleteById(id)).thenReturn(true);
        }
    }
}