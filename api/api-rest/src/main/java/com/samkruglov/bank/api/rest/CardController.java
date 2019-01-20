package com.samkruglov.bank.api.rest;

import com.samkruglov.bank.api.rest.view.request.dto.CardReqDto;
import com.samkruglov.bank.api.rest.view.response.dto.CardDto;
import com.samkruglov.bank.api.rest.view.response.mapper.CardMapper;
import com.samkruglov.bank.domain.Card;
import com.samkruglov.bank.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.samkruglov.bank.api.rest.view.response.ResponseUtil.created;
import static com.samkruglov.bank.api.rest.view.response.ResponseUtil.okOrNotFound;

@AllArgsConstructor
@RestController
@RequestMapping("api/bank/cards")
public class CardController {

    private final CardService service;
    private final CardMapper mapper;

    @GetMapping("{id}")
    public ResponseEntity<CardDto> get(@PathVariable Long id) {
        return okOrNotFound(service.get(id), mapper::toDto);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody CardReqDto accountDto, HttpServletRequest req) {
        Card card = service.create(accountDto.getAccountId(),
                                   accountDto.getExpiryDate(),
                                   accountDto.getIssueDate(),
                                   accountDto.getNumber()
        );
        return created(card, req);
    }

    @DeleteMapping("id")
    public ResponseEntity remove(@PathVariable Long id) {
        return okOrNotFound(service.remove(id));
    }
}
