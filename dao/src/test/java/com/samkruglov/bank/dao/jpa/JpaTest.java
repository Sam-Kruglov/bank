package com.samkruglov.bank.dao.jpa;

import com.samkruglov.bank.dao.interfaces.ClearDbBeforeTestMethod;
import com.samkruglov.bank.dao.jpa.config.JpaConfig;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Each test has no transaction - all actions within a test are immediately propagated to the database;
 * before each test, all tables are cleared with a special sql script.
 *
 * @implSpec concrete test classes <b>must not</b> use {@link org.junit.jupiter.api.Nested @Nested} annotations.
 * Because some functionality configured here will loose its effects
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Tag("long")
@Tag("context")
@DataJpaTest
@Import(JpaConfig.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@ClearDbBeforeTestMethod("classpath:/sql/clear_db_hsql.sql")
public @interface JpaTest {
}
