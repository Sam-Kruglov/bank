package com.samkruglov.bank.api.graphql.account;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.samkruglov.bank.api.graphql.view.dto.Account;
import com.samkruglov.bank.api.graphql.view.dto.Card;
import com.samkruglov.bank.api.graphql.view.dto.User;
import com.samkruglov.bank.api.graphql.view.mapper.CardGQMapper;
import com.samkruglov.bank.api.graphql.view.mapper.UserGQMapper;
import com.samkruglov.bank.service.AccountService;
import com.samkruglov.bank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AccountResolver implements GraphQLResolver<Account> {

    final UserGQMapper userMapper;
    final UserService userService;
    final AccountService service;
    final CardGQMapper cardMapper;

    public User getUser(Account account) {
        return userService.get(account.getUserId()).map(userMapper::toDto).get();
    }

    public Set<Card> getCards(Account account) {
        //todo create a dedicated repo method get cards by account id
        //noinspection Convert2MethodRef
        return service.get(account.getId())
                .map(a -> a.getReadOnlyCards())
                .map(cardMapper::toDtos)
                .orElse(Set.of());
    }
}
