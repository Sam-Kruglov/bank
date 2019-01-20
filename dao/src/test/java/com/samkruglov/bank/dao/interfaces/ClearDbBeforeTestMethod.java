package com.samkruglov.bank.dao.interfaces;

import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.jdbc.Sql;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Sql(executionPhase = BEFORE_TEST_METHOD)
public @interface ClearDbBeforeTestMethod {

    /**
     * Which script to use for clearing the database.
     */
    @AliasFor(annotation = Sql.class, attribute = "scripts")
    String[] value();
}
