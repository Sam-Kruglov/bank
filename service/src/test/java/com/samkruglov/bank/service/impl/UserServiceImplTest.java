package com.samkruglov.bank.service.impl;

import com.samkruglov.bank.dao.interfaces.UserRepo;
import com.samkruglov.bank.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    final UserRepo repo = mock(UserRepo.class);
    final UserServiceImpl service = new UserServiceImpl(repo);

    @DisplayName("user exists")
    @Nested
    class UserExists {

        final User user;

        UserExists() {
            user = mock(User.class);
            long id = 1L;
            when(user.getIdOptional()).thenReturn(Optional.of(id));
            when(user.getId()).thenReturn(id);
            when(repo.findById(id)).thenReturn(Optional.of(user));
            when(repo.deleteById(id)).thenReturn(true);
        }

        @DisplayName("get finds")
        @Test
        void get_found() {

            //arrange
            long userId = user.getId();

            //act & assert
            assertThat(service.get(userId)).contains(user);
        }

        @DisplayName("create persists")
        @Test
        void create_persisted() {

            //arrange
            String firstName = "J";
            String lastName = "S";

            //act
            service.create(firstName, lastName);

            //assert
            verify(repo).save(any());
        }

        @DisplayName("remove finds")
        @Test
        void remove_found() {

            //arrange
            long userId = user.getId();

            //act & assert
            assertThat(service.remove(userId)).isTrue();
        }
    }

    @DisplayName("user does not exist")
    @Nested
    class UserDoesNotExist {

        @DisplayName("get does not find")
        @Test
        void get_notFound() {

            //arrange
            long userId = 1L;

            //act & assert
            assertThat(service.get(userId)).isEmpty();
        }

        @DisplayName("create persists")
        @Test
        void create_persisted() {

            //arrange
            String firstName = "J";
            String lastName = "S";

            //act
            service.create(firstName, lastName);

            //assert
            verify(repo).save(any());
        }

        @DisplayName("remove does not find")
        @Test
        void remove_notFound() {

            //arrange
            long userId = 1L;

            //act & assert
            assertThat(service.remove(userId)).isFalse();
        }
    }
}