package com.samkruglov.bank.api.rest.view.response.dto;

import com.samkruglov.bank.api.rest.view.AuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CardDto extends AuditableDto<Long> {
    private LocalDate expiryDate;
    private LocalDate issueDate;
    private String number;
    private Long accountId;

    //todo Swagger sees only Optional
    public Optional<LocalDate> getIssueDate() {
        return Optional.ofNullable(issueDate);
    }

    public Optional<String> getNumber() {
        return Optional.ofNullable(number);
    }
}
