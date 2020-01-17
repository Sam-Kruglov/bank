package com.samkruglov.bank.ui.util

import com.vaadin.flow.component.Component
import com.vaadin.flow.component.HasValue
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.orderedlayout.FlexLayout
import reactor.core.CorePublisher
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

fun Component.updateUi(action: () -> Unit) = ui.ifPresent { it.access { action(); it.push() } }

// todo check up on https://github.com/reactor/reactor-core/issues/2016
fun reactiveButton(text: String, reactiveClickListenerSupplier: () -> CorePublisher<*>) =
        Button(text).apply {
            addClickListener {
                isEnabled = false
                reactiveClickListenerSupplier().apply {
                    if (this is Mono) {
                        doOnTerminate { updateUi { isEnabled = true } }
                                .subscribeOn(Schedulers.parallel())
                                .subscribe()
                    } else {
                        (this as Flux).doOnTerminate { updateUi { isEnabled = true } }
                                .subscribeOn(Schedulers.parallel())
                                .subscribe()
                    }
                }
            }
        }

fun clear(vararg containers: HasValue<*, *>) = containers.forEach { it.clear() }

fun FlexLayout.removeAllWithId(id: String) =
        children.filter { it.id.orElse(null) == id }.forEach { remove(it) }
