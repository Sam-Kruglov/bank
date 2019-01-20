package com.samkruglov.bank.api.graphql.view.mapper;

import com.samkruglov.bank.api.graphql.view.dto.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(uses = {AccountGQMapper.class, EntityGQMapper.class})
public interface CardGQMapper {

    @Mapping(source = "account", target = "accountId")
    Card toDto(com.samkruglov.bank.domain.Card card);

    Set<Card> toDtos(Set<com.samkruglov.bank.domain.Card> cards);
}
