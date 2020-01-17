package com.samkruglov.bank.ui.config

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.subscription.SubscriptionTransport
import com.apollographql.apollo.subscription.WebSocketSubscriptionTransport
import okhttp3.OkHttpClient
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

@Configuration
@EnableConfigurationProperties(BackendProperties::class)
class VaadinConfig {

    @Bean
    fun apolloClient(
            properties: BackendProperties,
            httpClient: OkHttpClient,
            subscriptionTransportFactory: SubscriptionTransport.Factory
    ) = ApolloClient.builder()
            .serverUrl(properties.graphqlUrl)
            .okHttpClient(httpClient)
            .subscriptionTransportFactory(subscriptionTransportFactory)
            .build()

    @Bean
    fun subscriptionTransportFactory(httpClient: OkHttpClient, properties: BackendProperties) =
            WebSocketSubscriptionTransport.Factory(properties.webSocketUrl, httpClient)

    @Bean
    fun httpClient() = OkHttpClient.Builder()
            //todo check up on https://github.com/graphql-java-kickstart/graphql-spring-boot/issues/328
            .addNetworkInterceptor { chain ->
                chain.proceed(
                        chain.request()
                                .newBuilder()
                                .removeHeader(HttpHeaders.CONTENT_TYPE)
                                .header(HttpHeaders.CONTENT_TYPE,
                                        MediaType.APPLICATION_JSON_VALUE)
                                .build()
                )
            }.build()
}

@ConstructorBinding
@ConfigurationProperties(prefix = "backend")
data class BackendProperties(val graphqlUrl: String, val webSocketUrl: String)