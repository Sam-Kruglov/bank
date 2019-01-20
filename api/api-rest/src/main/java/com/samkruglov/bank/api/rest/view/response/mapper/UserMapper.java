package com.samkruglov.bank.api.rest.view.response.mapper;

import com.samkruglov.bank.api.rest.view.response.mapper.common.OptionalMapper;
import com.samkruglov.bank.api.rest.view.response.dto.UserDto;
import com.samkruglov.bank.api.rest.view.response.dto.UserWithAccountIdsDto;
import com.samkruglov.bank.api.rest.view.response.mapper.common.IdentifiableMapper;
import com.samkruglov.bank.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {AccountMapper.class, OptionalMapper.class, IdentifiableMapper.class})
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(source = "readOnlyAccounts", target = "accountIds")
    UserWithAccountIdsDto toDtoWithIds(User user);
}
