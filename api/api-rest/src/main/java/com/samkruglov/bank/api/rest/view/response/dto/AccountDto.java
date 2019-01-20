package com.samkruglov.bank.api.rest.view.response.dto;

import com.samkruglov.bank.api.rest.view.AuditableDto;
import com.samkruglov.bank.domain.Account;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AccountDto extends AuditableDto<Long> {
    Long userId;
    Account.Type type;
}
