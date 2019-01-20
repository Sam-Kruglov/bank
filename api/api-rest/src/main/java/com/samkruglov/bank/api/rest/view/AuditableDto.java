package com.samkruglov.bank.api.rest.view;

import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
public class AuditableDto<T extends Serializable> {
    T id;
    ZonedDateTime dateCreated;
    ZonedDateTime lastUpdated;
}
