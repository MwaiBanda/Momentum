package com.mwaibanda.momentum.di

import com.mwaibanda.momentum.data.db.DatabaseDriverFactory
import com.russhwolf.settings.Settings
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import io.github.mwaibanda.authentication.di.Authentication
import io.github.mwaibanda.authentication.domain.controller.AuthenticationController
import io.github.reactivecircus.cache4k.Cache
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
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
                val timeout = 30000L
                connectTimeoutMillis = timeout
                requestTimeoutMillis = timeout
                socketTimeoutMillis = timeout
            }
        }
    }
    single { Settings() }
    single { Firebase.firestore }
    single { Authentication }
    single { Authentication.controller }
    single<Cache<String, Any>>{
        Cache.Builder()
            .expireAfterWrite(24.hours)
            .build()
    }
}