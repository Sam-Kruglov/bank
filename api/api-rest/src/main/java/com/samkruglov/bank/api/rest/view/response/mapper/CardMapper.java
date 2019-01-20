package com.samkruglov.bank.api.rest.view.response.mapper;

import com.samkruglov.bank.api.rest.view.response.mapper.common.OptionalMapper;
import com.samkruglov.bank.api.rest.view.response.dto.CardDto;
import com.samkruglov.bank.api.rest.view.response.mapper.common.IdentifiableMapper;
import com.samkruglov.bank.domain.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {AccountMapper.class, OptionalMapper.class, IdentifiableMapper.class})
public interface CardMapper {

    @Mapping(source = "account", target = "accountId")
    CardDto toDto(Card card);
}
