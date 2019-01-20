package com.samkruglov.bank.api.graphql.view.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends Auditable<Long> {
    private String firstName;
    private String lastName;
}
