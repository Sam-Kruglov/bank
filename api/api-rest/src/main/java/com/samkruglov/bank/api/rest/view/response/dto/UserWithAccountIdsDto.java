package com.samkruglov.bank.api.rest.view.response.dto;

import com.samkruglov.bank.api.rest.view.AuditableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserWithAccountIdsDto extends AuditableDto<Long> {
    private String firstName;
    private String lastName;
    private Set<Long> accountIds;
}
