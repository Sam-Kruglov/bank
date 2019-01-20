package com.samkruglov.bank;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserControllerTest {

    static final String BASE_PATH = "/api/bank/users";

    final MockMvc mvc;

    @Test
    void create_getFinds() throws Exception {

        String createdUri =
                mvc.perform(post(BASE_PATH)
                                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                                    //todo when request dtos are ready just build & serialize them here
                                    .content("{ \"firstName\": \"John\", \"lastName\": \"Smith\" }"))
                        .andReturn()
                        .getResponse()
                        .getHeader("location");

        mvc.perform(get(createdUri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName").value("John"))
                .andExpect(jsonPath("lastName").value("Smith"));
    }
}

