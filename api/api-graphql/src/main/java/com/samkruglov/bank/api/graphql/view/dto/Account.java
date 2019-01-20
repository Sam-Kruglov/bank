package com.samkruglov.bank.api.graphql.view.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Account extends Auditable<Long> {
    Long userId;
    com.samkruglov.bank.domain.Account.Type type;
}
