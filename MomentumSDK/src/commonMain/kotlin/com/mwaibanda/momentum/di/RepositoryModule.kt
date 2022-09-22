package com.mwaibanda.momentum.di

import com.mwaibanda.momentum.data.repository.*
import com.mwaibanda.momentum.data.repository.AuthRepositoryImpl
import com.mwaibanda.momentum.data.repository.PaymentRepositoryImpl
import com.mwaibanda.momentum.domain.repository.*
import org.koin.dsl.module
import kotlin.math.sin

val repositoryModule = module {
    single<PaymentRepository> { PaymentRepositoryImpl(httpClient = get()) }
    single<AuthRepository> { AuthRepositoryImpl(firebaseAuth = get()) }
    single<UserRepository> { UserRepositoryImpl(db = get()) }
    single<LocalDefaultsRepository> { LocalDefaultsRepositoryImpl(settings = get()) }
    single<CacheRepository> { CacheRepositoryImpl(cache = get()) }
    single<SermonRepository> {
        SermonRepositoryImpl(
            httpClient = get(),
            getItemUseCase = get(),
            setItemUseCase = get()
        )
    }
}