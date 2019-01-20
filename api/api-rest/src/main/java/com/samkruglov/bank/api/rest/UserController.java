package com.samkruglov.bank.api.rest;

import com.samkruglov.bank.api.rest.view.request.dto.UserReqDto;
import com.samkruglov.bank.api.rest.view.response.dto.UserDto;
import com.samkruglov.bank.api.rest.view.response.dto.UserWithAccountIdsDto;
import com.samkruglov.bank.api.rest.view.response.mapper.UserMapper;
import com.samkruglov.bank.domain.User;
import com.samkruglov.bank.service.UserService;
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
@RequestMapping("api/bank/users")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping("{id}")
    public ResponseEntity<UserDto> get(@PathVariable Long id) {
        return okOrNotFound(service.get(id), mapper::toDto);
    }

    @GetMapping("{id}/with-account-ids")
    public ResponseEntity<UserWithAccountIdsDto> getWithAccountIds(@PathVariable Long id) {
        return okOrNotFound(service.getWithAccounts(id), mapper::toDtoWithIds);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody UserReqDto userDto, HttpServletRequest req) {
        //todo use javabean validations on request dtos https://www.baeldung.com/spring-mvc-custom-validator
        User user = service.create(userDto.getFirstName(), userDto.getLastName());
        return created(user, req);
    }

    @DeleteMapping("id")
    public ResponseEntity remove(@PathVariable Long id) {
        return okOrNotFound(service.remove(id));
    }
}
