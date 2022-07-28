package com.mwaibanda.momentum.di

import com.mwaibanda.momentum.domain.usecase.*
import org.koin.dsl.module

internal val useCasesModule = module {
    single { CheckoutUseCase(paymentRepository = get()) }
    single { SignInWithEmailUseCase(authRepository = get()) }
    single { SignUpWithEmailUseCase(authRepository = get()) }
    single { SignInAsGuestUseCase(authRepository = get()) }
    single { PostUserUseCase(userRepository = get()) }
    single { UpdateUserEmailUseCase(userRepository = get()) }
    single { UpdateUserFullnameUseCase(userRepository = get()) }
    single { UpdateUserPhoneUseCase(userRepository = get()) }
    single { DeleteUserUseCase(userRepository = get()) }
}
