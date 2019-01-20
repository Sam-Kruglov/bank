package com.samkruglov.bank.api.rest.view.response.mapper.common;

import com.samkruglov.bank.domain.common.Identifiable;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
class IdentifiableMapperTest {

    private final IdentifiableMapper mapper = Mappers.getMapper(IdentifiableMapper.class);

    @Test
    void toIds() {

        //arrange
        Long one = 1L;
        Long two = 2L;
        Identifiable<Long> entity1 = mock(Identifiable.class);
        when(entity1.getId()).thenReturn(one);
        Identifiable<Long> entity2 = mock(Identifiable.class);
        when(entity2.getId()).thenReturn(two);

        Set<Identifiable<Long>> entities = Set.of(entity1, entity2);

        //act & assert
        assertThat(mapper.toIds(entities)).containsExactly(one, two);
    }

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