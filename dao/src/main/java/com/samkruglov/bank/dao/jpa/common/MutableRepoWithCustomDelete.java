package com.samkruglov.bank.dao.jpa.common;

import com.samkruglov.bank.dao.interfaces.common.MutableRepo;
import com.samkruglov.bank.domain.common.Identifiable;

import java.io.Serializable;
import java.util.Optional;

public interface MutableRepoWithCustomDelete<E extends Identifiable<ID>, ID extends Serializable>
        extends MutableRepo<E, ID> {

    void delete(E entity);

    Optional<E> findById(ID id);

    /**
     * @implNote could be implemented with Spring Data JPA derived query like {@code long deleteById(ID id)}
     * and check if returned value equals one but this approach would generate more queries when the entity is
     * in persistence context.
     */
    @Override
    default boolean deleteById(ID id) {
        return findById(id).map(e -> {
                                    delete(e);
                                    return true;
                                }
        ).orElse(false);
    }
}
