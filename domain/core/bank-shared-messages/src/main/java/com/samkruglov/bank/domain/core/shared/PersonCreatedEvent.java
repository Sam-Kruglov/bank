package com.samkruglov.bank.domain.core.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class PersonCreatedEvent {
    String id;
    String firstName;
    String lastName;
    String email;
    String phone;
}