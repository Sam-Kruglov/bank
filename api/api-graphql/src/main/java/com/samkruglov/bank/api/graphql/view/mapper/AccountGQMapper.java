package com.samkruglov.bank.api.graphql.view.mapper;

import com.samkruglov.bank.api.graphql.view.dto.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(uses = {UserGQMapper.class, CardGQMapper.class, EntityGQMapper.class})
public interface AccountGQMapper {

    @Mapping(source = "user", target = "userId")
    Account toDto(com.samkruglov.bank.domain.Account account);

    Set<Account> toDtos(Set<com.samkruglov.bank.domain.Account> account);
}
