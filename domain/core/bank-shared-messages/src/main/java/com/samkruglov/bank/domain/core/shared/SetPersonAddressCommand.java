package com.samkruglov.bank.domain.core.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.jetbrains.annotations.Nullable;

@Value
@AllArgsConstructor
@Builder
public class SetPersonAddressCommand {
    @TargetAggregateIdentifier
    String personId;
    @Nullable String apartmentNumber;
    String houseNumber;
    String street;
    String city;
    String countryCode;
}