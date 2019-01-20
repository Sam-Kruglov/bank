package com.samkruglov.bank.dao.jpa.config;

import com.samkruglov.bank.dao.jpa.AccountRepoJpa;
import com.samkruglov.bank.domain.User;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackageClasses = AccountRepoJpa.class)
@EntityScan(basePackageClasses = User.class)
@Configuration
public class JpaConfig {
}
