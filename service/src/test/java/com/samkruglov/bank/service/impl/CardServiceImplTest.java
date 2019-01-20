package com.samkruglov.bank.service.impl;

import com.samkruglov.bank.dao.interfaces.AccountRepo;
import com.samkruglov.bank.dao.interfaces.CardRepo;
import com.samkruglov.bank.domain.Account;
import com.samkruglov.bank.domain.Card;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CardServiceImplTest {

    final CardRepo repo = mock(CardRepo.class);
    final AccountRepo accountRepo = mock(AccountRepo.class);
    final CardServiceImpl service = new CardServiceImpl(accountRepo, repo);

    @DisplayName("account exists")
    @Nested
    class AccountExists {

        final Account account;

        AccountExists() {
            account = mock(Account.class, InvocationOnMock::callRealMethod);
            ReflectionTestUtils.setField(account, "cards", new HashSet<Card>());
            long id = 1L;
            when(account.getIdOptional()).thenReturn(Optional.of(id));
            when(account.getId()).thenReturn(id);
            when(accountRepo.findById(id)).thenReturn(Optional.of(account));
            when(accountRepo.findWithCardsById(id)).thenReturn(Optional.of(account));
        }

        @DisplayName("create persists")
        @Test
        void create_persisted() {

            //arrange
            long accountId = account.getId();
            LocalDate expiryDate = LocalDate.now();
            LocalDate issueDate = LocalDate.now();
            String number = null;

            //act
            service.create(accountId, expiryDate, issueDate, number);

            //assert
            verify(accountRepo).save(account);
        }

        @DisplayName("remove does not find")
        @Test
        void remove_notFound() {

            //arrange
            long cardId = 1L;

            //act & assert
            assertThat(service.remove(cardId)).isFalse();
        }

        @DisplayName("card exists")
        @Nested
        class CardExists {

            final Card card;

            CardExists() {

                card = new Card(LocalDate.now(), LocalDate.now(), null);
                account.addCard(card);
                long id = 1L;
                ReflectionTestUtils.setField(card, "id", id);
                when(repo.findById(id)).thenReturn(Optional.of(card));
            }

            @DisplayName("get finds")
            @Test
            void get_found() {

                //arrange
                long cardId = card.getId();

                //act & assert
                assertThat(service.get(cardId)).contains(card);
            }

            @DisplayName("create throws 'exists'")
            @Test
            void create_persisted() {

                //todo remove tests that rely on logic defined in domain
                //todo for this case add a test for Card#equals

                //arrange
                long accountId = account.getId();
                LocalDate expiryDate = LocalDate.now();
                LocalDate issueDate = LocalDate.now();
                String number = null;

                //act & assert
                assertThatIllegalArgumentException()
                        .isThrownBy(() -> service.create(accountId,
                                                         expiryDate,
                                                         issueDate,
                                                         number)
                        ).withMessageContaining("exists");
            }

            @DisplayName("remove finds")
            @Test
            void remove_found() {

                //arrange
                long cardId = card.getId();

                //act & assert
                assertThat(service.remove(cardId)).isTrue();
            }
        }
    }

    @DisplayName("account does not exist")
    @Nested
    class AccountDoesNotExists {

        @DisplayName("get does not find")
        @Test
        void get_notFound() {

            //arrange
            long cardId = 1L;

            //act & assert
            assertThat(service.get(cardId)).isEmpty();
        }

        @DisplayName("create throws 'not found'")
        @Test
        void create_exception() {

            //arrange
            long accountId = 1L;
            LocalDate expiryDate = LocalDate.now();
            LocalDate issueDate = LocalDate.now();
            String number = null;

            //act & assert
            assertThatIllegalArgumentException()
                    .as("cannot create a card for a non existent account")
                    .isThrownBy(() -> service.create(accountId,
                                                     expiryDate,
                                                     issueDate,
                                                     number))
                    .withMessageContaining("Could not find");
        }

        @DisplayName("remove throws 'not found'")
        @Test
        void remove_exception() {

            //arrange
            long cardId = 1L;

            //act & assert
            assertThatIllegalArgumentException()
                    .as("cannot remove a card from a non existent account")
                    .isThrownBy(() -> service.remove(cardId))
                    .withMessageContaining("Could not find");
        }
    }
}