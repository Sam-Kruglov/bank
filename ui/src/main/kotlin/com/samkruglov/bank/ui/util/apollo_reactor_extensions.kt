package com.samkruglov.bank.ui.util

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Mutation
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.Subscription
import com.apollographql.apollo.rx2.Rx2Apollo
import com.samkruglov.bank.ui.error.GraphQLException
import io.reactivex.BackpressureStrategy
import reactor.adapter.rxjava.RxJava2Adapter
import reactor.core.Exceptions
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

fun <D : Operation.Data, T, V : Operation.Variables>
        ApolloClient.mutateR(mutation: Mutation<D, T, V>): Mono<T> {
    val apolloMutationCall = mutate(mutation)
    val observable = Rx2Apollo.from(apolloMutationCall)
    return RxJava2Adapter.observableToFlux(observable, BackpressureStrategy.BUFFER)
            .single()
            .flatMap { unwrapErrors(it) }
}

fun <D : Operation.Data, T, V : Operation.Variables>
        ApolloClient.subscribeR(subscription: Subscription<D, T, V>): Flux<T> {
    val apolloSubscriptionCall = subscribe(subscription)
    val flowable = Rx2Apollo.from(apolloSubscriptionCall, BackpressureStrategy.BUFFER)
    return RxJava2Adapter.flowableToFlux(flowable).flatMap {
        unwrapErrors(it)
    }
}

private fun <T> unwrapErrors(response: Response<T>): Mono<T> {
    if (response.hasErrors()) {
        return Mono.error<T>(Exceptions.multiple(response.errors().map { e ->
            GraphQLException(e.message(), e.customAttributes())
        }))
    }
    return Mono.justOrEmpty(response.data())
}