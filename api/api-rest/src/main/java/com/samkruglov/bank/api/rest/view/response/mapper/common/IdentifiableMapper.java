package com.samkruglov.bank.api.rest.view.response.mapper.common;

import com.samkruglov.bank.domain.common.Identifiable;
import org.mapstruct.Mapper;

import java.io.Serializable;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Mapper
public interface IdentifiableMapper {

    default <ID extends Serializable, E extends Identifiable<ID>>
    Set<ID> toIds(Set<E> set) {
        return set.stream().map(this::toId).collect(toSet());
    }

    default <ID extends Serializable, E extends Identifiable<ID>>
    ID toId(E entity) {
        return entity.getId();
    }
}
