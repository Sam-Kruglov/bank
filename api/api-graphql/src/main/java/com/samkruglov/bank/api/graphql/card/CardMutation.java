package com.samkruglov.bank.api.graphql.card;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.samkruglov.bank.api.graphql.view.dto.Card;
import com.samkruglov.bank.api.graphql.view.mapper.CardGQMapper;
import com.samkruglov.bank.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@AllArgsConstructor
@Controller
public class CardMutation implements GraphQLMutationResolver {

    private final CardService service;
    private final CardGQMapper mapper;

    public Card createCard(
            Long accountId,
            LocalDate expiryDate,
            LocalDate cardIssueDate,
            String number) {

        com.samkruglov.bank.domain.Card domain = service.create(accountId,
                                                                expiryDate,
                                                                cardIssueDate,
                                                                number);
        return mapper.toDto(domain);
    }

    public boolean removeCard(Long id) {
        return service.remove(id);
    }
}
