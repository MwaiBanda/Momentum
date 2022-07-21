package com.mwaibanda.momentum.di

import com.mwaibanda.momentum.data.repository.AuthRepositoryImpl
import com.mwaibanda.momentum.data.repository.PaymentRepositoryImpl
import com.mwaibanda.momentum.domain.repository.AuthRepository
import com.mwaibanda.momentum.domain.repository.PaymentRepository
import org.koin.dsl.module

internal val repositoryModule = module {
    single<PaymentRepository>{  PaymentRepositoryImpl(httpClient = get()) }
    single<AuthRepository>{ AuthRepositoryImpl(firebaseAuth = get()) }
}