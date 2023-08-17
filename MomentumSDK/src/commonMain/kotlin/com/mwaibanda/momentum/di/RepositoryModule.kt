package com.mwaibanda.momentum.di

import com.mwaibanda.momentum.data.repository.*
import com.mwaibanda.momentum.data.repository.PaymentRepositoryImpl
import com.mwaibanda.momentum.domain.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    single<PaymentRepository> { PaymentRepositoryImpl(httpClient = get()) }
    single<TransactionRepository> { TransactionRepositoryImpl(httpClient = get()) }
    single<UserRepository> { UserRepositoryImpl(httpClient = get()) }
    single<LocalDefaultsRepository> { LocalDefaultsRepositoryImpl(settings = get()) }
    single<CacheRepository> { CacheRepositoryImpl(cache = get()) }
    single<SermonRepository> {
        SermonRepositoryImpl(
            httpClient = get(),
            getItemUseCase = get(),
            setItemUseCase = get(),
            getAllItemsUseCase = get()
        )
    }
    single<MealRepository> { MealRepositoryImpl(httpClient = get()) }
}