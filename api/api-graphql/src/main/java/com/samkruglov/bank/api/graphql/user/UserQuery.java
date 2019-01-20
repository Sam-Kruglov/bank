package com.samkruglov.bank.api.graphql.user;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.samkruglov.bank.api.graphql.view.dto.User;
import com.samkruglov.bank.api.graphql.view.mapper.UserGQMapper;
import com.samkruglov.bank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@AllArgsConstructor
@Controller
public class UserQuery implements GraphQLQueryResolver {

    //todo write tests for graphql using apollo Kotlin client

    private final UserService service;
    private final UserGQMapper mapper;

    public Optional<User> user(Long id) {
        return service.get(id).map(mapper::toDto);
    }
}
