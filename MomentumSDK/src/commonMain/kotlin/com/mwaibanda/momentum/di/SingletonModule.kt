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
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

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
                val timeout = 45000L
                connectTimeoutMillis = timeout
                requestTimeoutMillis = timeout
                socketTimeoutMillis = timeout
            }
            install(HttpRequestRetry) {
                retryOnExceptionOrServerErrors(maxRetries = 5)
                exponentialDelay(
                    maxDelayMs = 90000,
                    randomizationMs = 30000
                )
            }
        }
    }
    single { Settings() }
    single { Authentication }
    single { Authentication.controller }
    single<Cache<String, Any>>{
        Cache.Builder()
            .expireAfterWrite(2.hours + 30.minutes)
            .build()
    }
}