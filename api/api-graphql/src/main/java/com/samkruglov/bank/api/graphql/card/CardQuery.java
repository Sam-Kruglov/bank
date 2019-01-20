package com.samkruglov.bank.api.graphql.card;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.samkruglov.bank.api.graphql.view.dto.Card;
import com.samkruglov.bank.api.graphql.view.mapper.CardGQMapper;
import com.samkruglov.bank.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@AllArgsConstructor
@Controller
public class CardQuery implements GraphQLQueryResolver {

    private final CardService service;
    private final CardGQMapper mapper;

    public Optional<Card> card(Long id) {
        return service.get(id).map(mapper::toDto);
    }
}
