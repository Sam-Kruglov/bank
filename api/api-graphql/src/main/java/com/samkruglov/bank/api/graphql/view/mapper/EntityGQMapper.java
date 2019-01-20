package com.samkruglov.bank.api.graphql.view.mapper;

import com.samkruglov.bank.domain.common.Identifiable;
import org.mapstruct.Mapper;

import java.io.Serializable;
import java.util.Optional;

@Mapper
public interface EntityGQMapper {

    default <T> T unwrap(Optional<T> optional) {
        return optional.orElse(null);
    }

    default <ID extends Serializable, E extends Identifiable<ID>>
    ID toId(E entity) {
        return entity.getId();
    }
}
