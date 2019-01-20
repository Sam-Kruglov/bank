package com.samkruglov.bank.api.rest.view.request.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import java.time.LocalDate;

@Value
public class CardReqDto {
    Long accountId;

    //todo if "bla" passed then jackson is confused
    @ApiModelProperty(example = "2016-01-01")
    LocalDate expiryDate;

    @ApiModelProperty(example = "2016-01-01")
    LocalDate issueDate;

    String number;
}
