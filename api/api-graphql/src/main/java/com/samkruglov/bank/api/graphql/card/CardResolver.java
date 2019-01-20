package com.samkruglov.bank.api.graphql.card;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.samkruglov.bank.api.graphql.view.dto.Account;
import com.samkruglov.bank.api.graphql.view.dto.Card;
import com.samkruglov.bank.api.graphql.view.mapper.AccountGQMapper;
import com.samkruglov.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CardResolver implements GraphQLResolver<Card> {

    final AccountGQMapper accountMapper;
    final AccountService accountService;

    public Account getAccount(Card card) {
        return accountService.get(card.getAccountId()).map(accountMapper::toDto).get();
    }
}
