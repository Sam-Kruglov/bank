package com.samkruglov.bank.api.rest.view.response.mapper.common;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class OptionalMapperTest {

    private final OptionalMapper mapper = Mappers.getMapper(OptionalMapper.class);

    @Test
    void unwrap_present_value() {

        Long one = 1L;
        Optional<Long> optional = Optional.of(one);

        assertThat(mapper.unwrap(optional)).isSameAs(one);
    }

    @Test
    void unwrap_empty_null() {

        Optional<Long> optional = Optional.ofNullable(null);

        assertThat(mapper.unwrap(optional)).isSameAs(null);
    }
}