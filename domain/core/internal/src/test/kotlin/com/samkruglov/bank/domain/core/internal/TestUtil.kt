package com.samkruglov.bank.domain.core.internal

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.ObjectAssert
import org.assertj.core.matcher.AssertionMatcher
import org.axonframework.test.aggregate.ResultValidator
import org.axonframework.test.aggregate.TestExecutor
import org.axonframework.test.matchers.Matchers.exactSequenceOf
import org.axonframework.test.matchers.Matchers.payloadsMatching
import org.hamcrest.Matcher

fun <T> TestExecutor<T>.whenever(command: Any) = `when`(command)


fun <T> ResultValidator<*>.expectEventThat(assertion: ObjectAssert<T>.() -> Unit): ResultValidator<*> {
    return expectEventsMatching(payloadsMatching(exactSequenceOf(
            asserting<T> { assertion(assertThat(it)) }
    )))
}

fun <T> ResultValidator<*>.expectEventThat(vararg assertions: ObjectAssert<T>.() -> Unit): ResultValidator<*> {
    return expectEventsMatching(payloadsMatching(exactSequenceOf(
            org.hamcrest.Matchers.allOf(
                    assertions.map { assertion ->
                        asserting<T> { assertion(assertThat(it)) }
                    }
            )
    )))
}

fun <T> asserting(consumer: (T) -> Unit): Matcher<T> = object : AssertionMatcher<T>() {
    override fun assertion(actual: T) {
        consumer(actual)
    }
}
