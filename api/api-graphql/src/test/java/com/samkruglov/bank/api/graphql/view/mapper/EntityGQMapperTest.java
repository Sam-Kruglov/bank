package com.samkruglov.bank.api.graphql.view.mapper;

import com.samkruglov.bank.domain.common.Identifiable;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EntityGQMapperTest {

    private final EntityGQMapper mapper = Mappers.getMapper(EntityGQMapper.class);

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

    @SuppressWarnings("unchecked")
    @Test
    void toId() {

        //arrange
        Long one = 1L;
        Identifiable<Long> entity = mock(Identifiable.class);
        when(entity.getId()).thenReturn(one);

        //act & assert
        assertThat(mapper.toId(entity)).isEqualTo(one);
    }
}