package com.samkruglov.bank.api.graphql.user;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.samkruglov.bank.api.graphql.view.dto.User;
import com.samkruglov.bank.api.graphql.view.mapper.UserGQMapper;
import com.samkruglov.bank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
public class UserMutation implements GraphQLMutationResolver {

    private final UserService service;
    private final UserGQMapper mapper;

    public User createUser(String firstName, String lastName) {
        com.samkruglov.bank.domain.User domain = service.create(firstName, lastName);
        return mapper.toDto(domain);
    }

    public boolean removeUser(Long id) {
        return service.remove(id);
    }
}
