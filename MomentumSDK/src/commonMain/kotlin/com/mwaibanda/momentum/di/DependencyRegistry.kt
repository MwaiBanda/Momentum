package com.mwaibanda.momentum.di

import com.mwaibanda.momentum.controller.PaymentControllerImpl
import com.mwaibanda.momentum.data.repository.PaymentRepositoryImpl
import com.mwaibanda.momentum.domain.controller.PaymentController
import com.mwaibanda.momentum.domain.repository.PaymentRepository
import com.mwaibanda.momentum.domain.usecase.CheckoutUseCase
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            singletonModule,
            repositoryModule,
            useCasesModule,
            controllerModule
        )
    }

// IOS
fun initKoin() = initKoin {
    modules(
        singletonModule,
        repositoryModule,
        useCasesModule,
        controllerModule
    )
}

val singletonModule = module {
    single {
        HttpClient {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(JsonFeature) {
                val json = kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                }
                serializer = KotlinxSerializer(json)
            }
            install(HttpTimeout) {
                val timeout = 30000L
                connectTimeoutMillis = timeout
                requestTimeoutMillis = timeout
                socketTimeoutMillis = timeout
            }
            ResponseObserver { response ->
                println("HTTP status: ${response.status.value}")
            }
        }
    }
}

val repositoryModule = module {
    single<PaymentRepository>{  PaymentRepositoryImpl(get()) }
}

val useCasesModule = module {
    single { CheckoutUseCase(get()) }
}

val controllerModule = module {
    single<PaymentController>{  PaymentControllerImpl() }
}
