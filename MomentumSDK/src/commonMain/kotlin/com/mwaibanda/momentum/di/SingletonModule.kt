package com.mwaibanda.momentum.di

import com.russhwolf.settings.Settings
import io.github.mwaibanda.authentication.di.Authentication
import io.github.reactivecircus.cache4k.Cache
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import kotlin.time.Duration.Companion.hours

val singletonModule = module {
    single {
        HttpClient {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(HttpTimeout) {
                val timeout = 60000L
                connectTimeoutMillis = timeout
                requestTimeoutMillis = timeout
                socketTimeoutMillis = timeout
            }
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 5)
                retryOnExceptionIf(maxRetries = 5) { request, cause ->
                    cause is TimeoutCancellationException ||
                    cause is CancellationException ||
                    cause is kotlinx.coroutines.CancellationException
                }
                exponentialDelay()
            }
        }
    }
    single { Settings() }
    single { Authentication }
    single { Authentication.controller }
    single<Cache<String, Any>>{
        Cache.Builder()
            .expireAfterWrite(24.hours)
            .build()
    }
}