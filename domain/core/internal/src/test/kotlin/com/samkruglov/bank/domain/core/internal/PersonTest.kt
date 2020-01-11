package com.samkruglov.bank.domain.core.internal

import com.samkruglov.bank.domain.core.internal.config.CoreMapper
import com.samkruglov.bank.domain.core.shared.CreatePersonCommand
import com.samkruglov.bank.domain.core.shared.PersonAddressSetEvent
import com.samkruglov.bank.domain.core.shared.PersonCreatedEvent
import com.samkruglov.bank.domain.core.shared.SetPersonAddressCommand
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.axonframework.test.aggregate.TestExecutor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

class PersonTest {

    lateinit var fixture: FixtureConfiguration<Person>
    val mapper = Mappers.getMapper(CoreMapper::class.java)

    @BeforeEach
    fun setUp() {
        fixture = AggregateTestFixture(Person::class.java).registerInjectableResource(mapper)
    }

    @Nested
    inner class `create a person` {

        val createPersonCommand = CreatePersonCommand("josh", "long", "josh.long@vmware.com", "+1-415-123-1234")
        val personCreatedEvent = mapper.toEvent("id", createPersonCommand)

        @Test
        fun created() {
            fixture.givenNoPriorActivity()
                    .whenever(createPersonCommand)
                    .expectEventThat<PersonCreatedEvent>(
                            { usingRecursiveComparison().ignoringFields("id").isEqualTo(personCreatedEvent) },
                            { extracting { it.id }.isNotNull() }
                    )
        }
    }

    @Nested
    inner class `given a person exists` {

        val person = PersonCreatedEvent("1", "josh", "long", "josh.long@vmware.com", "+1-415-123-1234")

        lateinit var givenPersonExists: TestExecutor<Person>

        @BeforeEach
        fun setUp() {
            givenPersonExists = fixture.given(person)
        }

        @Nested
        inner class `assign an address to a person` {

            val setPersonAddressCommand = SetPersonAddressCommand(
                    person.id,
                    null,
                    "1",
                    "Fell",
                    "San Francisco",
                    "USA"
            )
            val personAddressSetEvent = mapper.toEvent(setPersonAddressCommand)

            @Test
            fun assigned() {
                givenPersonExists.whenever(setPersonAddressCommand)
                        .expectEventThat<PersonAddressSetEvent> { isEqualTo(personAddressSetEvent) }
            }
        }

        @Nested
        inner class `given an address is present` {

            val address = PersonAddressSetEvent(
                    person.id,
                    null,
                    "1",
                    "Fell",
                    "San Francisco",
                    "USA"
            )

            lateinit var givenAddressIsPresent: TestExecutor<Person>

            @BeforeEach
            fun setUp() {
                givenAddressIsPresent = givenPersonExists.andGiven(address)
            }

            @Nested
            inner class `change address` {

                val setPersonAddressCommand = SetPersonAddressCommand(
                        person.id,
                        "3B",
                        "7",
                        "Lathrop Ave",
                        "Chicago",
                        "USA"
                )
                val personAddressSetEvent = mapper.toEvent(setPersonAddressCommand)

                @Test
                fun changed() {
                    givenAddressIsPresent.whenever(setPersonAddressCommand)
                            .expectEventThat<PersonAddressSetEvent> { isEqualTo(personAddressSetEvent) }
                }
            }
        }
    }
}