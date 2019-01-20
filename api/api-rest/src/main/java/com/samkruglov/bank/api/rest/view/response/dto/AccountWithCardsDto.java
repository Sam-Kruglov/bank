package com.samkruglov.bank.api.rest.view.response.dto;

import com.samkruglov.bank.api.rest.view.AuditableDto;
import com.samkruglov.bank.domain.Account;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AccountWithCardsDto extends AuditableDto<Long> {
    Long userId;
    Account.Type type;
    Set<Card> cards;

    @Data
    public static class Card {
        private Long id;
        private LocalDate expiryDate;
        private LocalDate issueDate;
        private String number;
    }
}
