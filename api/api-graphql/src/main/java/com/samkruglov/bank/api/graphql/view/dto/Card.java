package com.samkruglov.bank.api.graphql.view.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Card extends Auditable<Long> {
    private LocalDate expiryDate;
    private LocalDate issueDate;
    private String number;
    private Long accountId;

    public Optional<LocalDate> getIssueDate() {
        return Optional.ofNullable(issueDate);
    }

    public Optional<String> getNumber() {
        return Optional.ofNullable(number);
    }
}
