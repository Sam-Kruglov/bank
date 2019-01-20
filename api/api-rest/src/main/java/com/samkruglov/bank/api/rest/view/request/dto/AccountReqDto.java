package com.samkruglov.bank.api.rest.view.request.dto;

import com.samkruglov.bank.domain.Account;
import lombok.Value;

@Value
public class AccountReqDto {
    Long userId;
    Account.Type type;
}
