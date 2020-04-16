package com.samkruglov.bank.ui

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.samkruglov.bank.ui.gen.com.samkruglov.bank.CreateJoshMutation
import com.samkruglov.bank.ui.gen.com.samkruglov.bank.GetAllPeopleSubscription
import com.samkruglov.bank.ui.gen.com.samkruglov.bank.SetPersonAddressMutation
import com.samkruglov.bank.ui.util.clear
import com.samkruglov.bank.ui.util.mutateR
import com.samkruglov.bank.ui.util.reactiveButton
import com.samkruglov.bank.ui.util.subscribeR
import com.samkruglov.bank.ui.util.updateUi
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.annotation.SpringComponent
import com.vaadin.flow.spring.annotation.UIScope
import java.time.Duration
import java.util.*


@Route
class MainView(val client: ApolloClient, val peopleForm: PeopleForm) : HorizontalLayout() {

    companion object {
        private const val ADDRESS_FORM_ID = "ADDRESS_FORM"
    }

    init {
        add(peopleTable(), peopleForm)
    }

    fun peopleTable(): VerticalLayout {
        val peopleList = LinkedList<GetAllPeopleSubscription.People>()

        val peopleGrid = Grid<GetAllPeopleSubscription.People>()
        peopleGrid.maxWidth = "40%"
        peopleGrid.isHeightByRows = true
        peopleGrid.setItems(peopleList)
        peopleGrid.addColumn { "${it.firstName} ${it.lastName}" }.setHeader("Name")
        peopleGrid.addColumn {
            if (it.address == null) {
                return@addColumn "right click to set"
            }
            val address: GetAllPeopleSubscription.Address = it.address
            "${address.countryCode}, ${address.city}"
        }.setHeader("Address")
        val gridContextMenu = peopleGrid.addContextMenu()
        val setAddressOption = gridContextMenu.addItem("Set address") {
            updateUi {
                remove(peopleForm)
                children.filter { it.id.orElse(null) == ADDRESS_FORM_ID }.forEach { remove(it) }
                val doAfterAddressIsSet = {
                    updateUi {
                        add(peopleForm)
                        children.filter { it.id.orElse(null) == ADDRESS_FORM_ID }.forEach { remove(it) }
                    }
                }
                add(addressForm(it.item.get().id, doAfterAddressIsSet))
            }
        }

        setAddressOption.isEnabled = false
        gridContextMenu.addGridContextMenuOpenedListener {
            updateUi {
                if (it.item.isPresent) {
                    setAddressOption.isEnabled = true
                }
            }
        }

        val loadButton = reactiveButton("Load") {
            peopleList.clear()
            client.subscribeR(GetAllPeopleSubscription())
                    .map { it.people }
                    //delay is to make reactive approach more visible
                    .delayElements(Duration.ofMillis(50))
                    .doOnNext {
                        peopleList.add(it)
                        updateUi { peopleGrid.dataProvider.refreshAll() }
                    }
                    .log()
        }

        return VerticalLayout(loadButton, peopleGrid)
    }

    fun addressForm(id: String, doOnSuccess: () -> Unit): VerticalLayout {
        val apartmentNumberField = TextField("Apartment number")
        val houseNumberField = TextField("House number")
        val streetField = TextField("Street")
        val cityField = TextField("City")
        val countryCodeField = TextField("Country")

        val saveButton = reactiveButton("Save") {
            client.mutateR(SetPersonAddressMutation(
                    id,
                    apartmentNumberField.value.let { if (it.isEmpty()) Input.absent() else Input.fromNullable(it) },
                    houseNumberField.value,
                    streetField.value,
                    cityField.value,
                    countryCodeField.value
            ))
                    .doOnSuccess {
                        updateUi { Notification.show("Success", 1000, Notification.Position.BOTTOM_END) }
                        doOnSuccess()
                    }
                    .log()
        }

        val layout = VerticalLayout(
                HorizontalLayout(countryCodeField, cityField),
                HorizontalLayout(streetField, houseNumberField, apartmentNumberField),
                saveButton
        )
        layout.setId(ADDRESS_FORM_ID)
        return layout
    }
}

@SpringComponent
@UIScope
class PeopleForm(val client: ApolloClient) : VerticalLayout() {
    init {
        val firstNameField = TextField("First Name")
        val lastNameField = TextField("Last Name")
        val emailField = TextField("Email")
        val phoneField = TextField("Phone")

        val saveButton = reactiveButton("Save") {
            client.mutateR(CreateJoshMutation(
                    firstNameField.value,
                    lastNameField.value,
                    emailField.value,
                    phoneField.value
            ))
                    .doOnSuccess { updateUi { clear(firstNameField, lastNameField, emailField, phoneField) } }
                    .map { it.createPerson }
                    .doOnNext {
                        updateUi {
                            Notification.show("created Person #$it", 2000, Notification.Position.BOTTOM_END)
                        }
                    }
                    .log()
        }

        add(
                HorizontalLayout(firstNameField, lastNameField),
                HorizontalLayout(emailField, phoneField),
                saveButton
        )
    }
}
