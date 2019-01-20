package com.samkruglov.bank.api.graphql.account;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.samkruglov.bank.api.graphql.view.dto.Account;
import com.samkruglov.bank.api.graphql.view.mapper.AccountGQMapper;
import com.samkruglov.bank.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
public class AccountMutation implements GraphQLMutationResolver {

    private final AccountService service;
    private final AccountGQMapper mapper;

    public Account createAccount(Long userId, com.samkruglov.bank.domain.Account.Type type) {
        com.samkruglov.bank.domain.Account domain = service.create(userId, type);
        return mapper.toDto(domain);
    }

    public boolean removeAccount(Long id) {
        return service.remove(id);
    }
}
