package com.samkruglov.bank.dao.interfaces.common;

import com.samkruglov.bank.domain.common.Identifiable;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Function;

/**
 * Contains methods that only read data
 *
 * @param <ID> entity primary key type
 * @param <E> entity type
 */
public interface ImmutableRepo<E extends Identifiable<ID>, ID extends Serializable> {

    Optional<E> findById(ID id);

    /**
     * Unwraps the {@link Optional} throwing an exception.
     *
     * @param finder method reference to one of the methods here
     * @param id entity id
     * @param <ID> entity id type
     * @param <E> entity type
     * @return the {@code value} of the {@code Optional}
     * @throws IllegalArgumentException if the {@code Optional} is empty
     */
    static <ID extends Serializable, E extends Identifiable<ID>>
    E unwrapped(@NonNull Function<ID, Optional<E>> finder, @NonNull ID id) {
        return finder.apply(id)
                //todo "entity" is not specific enough
                .orElseThrow(() -> new IllegalArgumentException("Could not find entity with id #" + id));
    }
}
