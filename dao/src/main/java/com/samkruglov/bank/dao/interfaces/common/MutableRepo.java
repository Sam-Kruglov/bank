package com.samkruglov.bank.dao.interfaces.common;

import com.samkruglov.bank.domain.common.Identifiable;

import java.io.Serializable;

/**
 * Contains methods that only write data
 *
 * @param <ID> entity primary key type
 * @param <E> entity type
 */
public interface MutableRepo<E extends Identifiable<ID>, ID extends Serializable> {

    boolean deleteById(ID id);

    E save(E entity);
}
