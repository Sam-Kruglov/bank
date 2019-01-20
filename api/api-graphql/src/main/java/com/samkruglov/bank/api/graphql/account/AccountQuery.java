package com.samkruglov.bank.api.graphql.account;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.samkruglov.bank.api.graphql.view.dto.Account;
import com.samkruglov.bank.api.graphql.view.mapper.AccountGQMapper;
import com.samkruglov.bank.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@AllArgsConstructor
@Controller
//todo write controller tests
public class AccountQuery implements GraphQLQueryResolver {

    private final AccountService service;
    private final AccountGQMapper mapper;

    public Optional<Account> account(Long id) {
        return service.get(id).map(mapper::toDto);
    }
}
