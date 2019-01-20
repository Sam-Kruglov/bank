package com.samkruglov.bank.api.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static java.util.Collections.emptyList;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.service.ApiInfo.DEFAULT_CONTACT;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(SWAGGER_2)
                .apiInfo(new ApiInfo(
                        "Banking API",
                        "",
                        "1.0.0",
                        "",
                        DEFAULT_CONTACT,
                        "Apache 2.0",
                        "http://www.apache.org/licenses/LICENSE-2.0",
                        emptyList())
                )
                .select()
                .apis(basePackage("com.samkruglov"))
                .build();
    }
}

