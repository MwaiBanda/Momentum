package com.mwaibanda.momentum.di

import com.mwaibanda.momentum.data.repository.CacheRepositoryImpl
import com.mwaibanda.momentum.data.repository.EventRepositoryImpl
import com.mwaibanda.momentum.data.repository.LocalDefaultsRepositoryImpl
import com.mwaibanda.momentum.data.repository.MealRepositoryImpl
import com.mwaibanda.momentum.data.repository.MessageRepositoryImpl
import com.mwaibanda.momentum.data.repository.NotificationRepositoryImpl
import com.mwaibanda.momentum.data.repository.PaymentRepositoryImpl
import com.mwaibanda.momentum.data.repository.SermonRepositoryImpl
import com.mwaibanda.momentum.data.repository.TransactionRepositoryImpl
import com.mwaibanda.momentum.data.repository.UserRepositoryImpl
import com.mwaibanda.momentum.domain.repository.CacheRepository
import com.mwaibanda.momentum.domain.repository.EventRepository
import com.mwaibanda.momentum.domain.repository.LocalDefaultsRepository
import com.mwaibanda.momentum.domain.repository.MealRepository
import com.mwaibanda.momentum.domain.repository.MessageRepository
import com.mwaibanda.momentum.domain.repository.NotificationRepository
import com.mwaibanda.momentum.domain.repository.PaymentRepository
import com.mwaibanda.momentum.domain.repository.SermonRepository
import com.mwaibanda.momentum.domain.repository.TransactionRepository
import com.mwaibanda.momentum.domain.repository.UserRepository
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
    single<MealRepository> {
        MealRepositoryImpl(
            httpClient = get(),
            getItemUseCase = get(),
            setItemUseCase = get()
        )
    }
    single<MessageRepository> {
        MessageRepositoryImpl(
            httpClient = get(),
            getItemUseCase = get(),
            setItemUseCase = get()
        )
    }
    single<NotificationRepository> { NotificationRepositoryImpl(httpClient = get())  }
    single<EventRepository> {
        EventRepositoryImpl(
            httpClient = get(),
            getItemUseCase = get(),
            setItemUseCase = get()
        )
    }
}