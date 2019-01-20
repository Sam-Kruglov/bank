package com.samkruglov.bank.dao.interfaces.common;

import com.samkruglov.bank.domain.common.Identifiable;

import java.io.Serializable;

public interface Repo<E extends Identifiable<ID>, ID extends Serializable>
        extends ImmutableRepo<E, ID>,
        MutableRepo<E, ID> {
}
