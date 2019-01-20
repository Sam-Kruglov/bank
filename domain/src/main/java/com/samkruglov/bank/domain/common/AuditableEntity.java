package com.samkruglov.bank.domain.common;

import lombok.Getter;
import lombok.NonNull;

import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Getter
@MappedSuperclass
public class AuditableEntity<T extends Serializable> extends IdentifiableAdapter<T> {

    @NonNull
    private ZonedDateTime dateCreated = ZonedDateTime.now(ZoneOffset.UTC);
    @NonNull
    private ZonedDateTime lastUpdated = ZonedDateTime.now(ZoneOffset.UTC);

    @PreUpdate
    public void updateLastUpdated() {
        this.lastUpdated = ZonedDateTime.now(ZoneOffset.UTC);
    }

    @Override
    public String toString() {
        return '(' +
                super.toString() +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                ')';
    }
}
