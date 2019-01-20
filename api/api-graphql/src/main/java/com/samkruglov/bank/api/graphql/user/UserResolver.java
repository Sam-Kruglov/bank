package com.samkruglov.bank.api.graphql.user;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.samkruglov.bank.api.graphql.view.dto.Account;
import com.samkruglov.bank.api.graphql.view.dto.User;
import com.samkruglov.bank.api.graphql.view.mapper.AccountGQMapper;
import com.samkruglov.bank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class UserResolver implements GraphQLResolver<User> {

    final UserService service;
    final AccountGQMapper accountMapper;

    public Set<Account> getAccounts(User user) {
        //noinspection Convert2MethodRef
        return service.get(user.getId())
                .map(a -> a.getReadOnlyAccounts())
                .map(accountMapper::toDtos)
                .orElse(Set.of());
    }
}
