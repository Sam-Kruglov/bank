package com.samkruglov.bank.domain.core.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.jetbrains.annotations.Nullable;

//todo public messages and private messages should exist, may differ, and public are backwards-compatible

@Value
@AllArgsConstructor
@Builder
// can we store deltas instead of the whole address? using untyped json to differ "not changed" and "set to null"
public class PersonAddressSetEvent {
    String personId;
    @Nullable String apartmentNumber;
    String houseNumber;
    String street;
    String city;
    String countryCode;
}
