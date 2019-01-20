package com.samkruglov.bank.api.graphql.view.mapper;

import com.samkruglov.bank.api.graphql.view.dto.User;
import org.mapstruct.Mapper;

@Mapper(uses = {AccountGQMapper.class, EntityGQMapper.class})
public interface UserGQMapper {

    User toDto(com.samkruglov.bank.domain.User user);
}
