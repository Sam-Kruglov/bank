package com.samkruglov.bank.api.rest.view.response.mapper;

import com.samkruglov.bank.api.rest.view.response.mapper.common.OptionalMapper;
import com.samkruglov.bank.api.rest.view.response.dto.AccountDto;
import com.samkruglov.bank.api.rest.view.response.dto.AccountWithCardsDto;
import com.samkruglov.bank.api.rest.view.response.mapper.common.IdentifiableMapper;
import com.samkruglov.bank.domain.Account;
import com.samkruglov.bank.domain.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {UserMapper.class, CardMapper.class, OptionalMapper.class, IdentifiableMapper.class})
public abstract class AccountMapper {

    @Mapping(source = "readOnlyCards", target = "cards")
    @Mapping(source = "user", target = "userId")
    public abstract AccountWithCardsDto toDtoWithCards(Account account);

    protected abstract AccountWithCardsDto.Card toDto(Card card);

    @Mapping(source = "user", target = "userId")
    public abstract AccountDto toDto(Account account);
}
