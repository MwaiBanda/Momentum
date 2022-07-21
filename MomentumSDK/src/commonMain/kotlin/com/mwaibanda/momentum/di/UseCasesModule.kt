package com.mwaibanda.momentum.di

import com.mwaibanda.momentum.domain.usecase.CheckoutUseCase
import com.mwaibanda.momentum.domain.usecase.SignInAsGuestUseCase
import com.mwaibanda.momentum.domain.usecase.SignInWithEmailUseCase
import com.mwaibanda.momentum.domain.usecase.SignUpWithEmailUseCase
import org.koin.dsl.module

val useCasesModule = module {
    single { CheckoutUseCase(paymentRepository = get()) }
    single { SignInWithEmailUseCase(authRepository = get()) }
    single { SignUpWithEmailUseCase(authRepository = get()) }
    single { SignInAsGuestUseCase(authRepository = get()) }
}
