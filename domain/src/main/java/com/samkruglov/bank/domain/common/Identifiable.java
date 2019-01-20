package com.samkruglov.bank.domain.common;

import java.io.Serializable;
import java.util.Optional;

public interface Identifiable<T extends Serializable> {

    Optional<T> getIdOptional();

    T getId();
}