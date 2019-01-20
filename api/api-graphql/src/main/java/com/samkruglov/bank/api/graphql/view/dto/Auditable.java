package com.samkruglov.bank.api.graphql.view.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class Auditable<T extends Serializable> {
    T id;
    ZonedDateTime dateCreated;
    ZonedDateTime lastUpdated;
}
