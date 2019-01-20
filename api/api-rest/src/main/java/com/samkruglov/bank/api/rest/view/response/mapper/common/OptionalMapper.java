package com.samkruglov.bank.api.rest.view.response.mapper.common;

import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper
public interface OptionalMapper {

    default <T> T unwrap(Optional<T> optional) {
        return optional.orElse(null);
    }
}
