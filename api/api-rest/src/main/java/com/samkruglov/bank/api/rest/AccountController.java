package com.samkruglov.bank.api.rest;

import com.samkruglov.bank.api.rest.view.request.dto.AccountReqDto;
import com.samkruglov.bank.api.rest.view.response.dto.AccountDto;
import com.samkruglov.bank.api.rest.view.response.dto.AccountWithCardsDto;
import com.samkruglov.bank.api.rest.view.response.mapper.AccountMapper;
import com.samkruglov.bank.domain.Account;
import com.samkruglov.bank.service.AccountService;
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
@RequestMapping("api/bank/accounts")
//todo write controller tests
public class AccountController {

    private final AccountService service;
    private final AccountMapper mapper;

    @GetMapping("{id}")
    public ResponseEntity<AccountDto> get(@PathVariable Long id) {
        return okOrNotFound(service.get(id), mapper::toDto);
    }

    @GetMapping("{id}/with-cards")
    public ResponseEntity<AccountWithCardsDto> getWithCards(@PathVariable Long id) {
        return okOrNotFound(service.getWithCards(id), mapper::toDtoWithCards);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody AccountReqDto accountDto, HttpServletRequest req) {
        Account account = service.create(accountDto.getUserId(), accountDto.getType());
        return created(account, req);
    }

    @DeleteMapping("id")
    public ResponseEntity remove(@PathVariable Long id) {
        return okOrNotFound(service.remove(id));
    }
}
