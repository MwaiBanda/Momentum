package com.mwaibanda.momentum.di

import com.mwaibanda.momentum.domain.usecase.auth.*
import com.mwaibanda.momentum.domain.usecase.payment.CheckoutUseCase
import com.mwaibanda.momentum.domain.usecase.user.*
import org.koin.dsl.module

internal val useCasesModule = module {
    /**
     * @Payment - Use-cases
     */
    single { CheckoutUseCase(paymentRepository = get()) }
    /**
     * @Auth - Use-cases
     */
    single { SignInWithEmailUseCase(authRepository = get()) }
    single { SignUpWithEmailUseCase(authRepository = get()) }
    single { SignInAsGuestUseCase(authRepository = get()) }
    single { IsUserSignedInUseCase(authRepository = get()) }
    single { DeleteUserUseCase(authRepository = get()) }
    single { SignOutUseCase(authRepository = get()) }
    /**
     * @User - Use-case
     */
    single { PostUserUseCase(userRepository = get()) }
    single { UpdateUserEmailUseCase(userRepository = get()) }
    single { UpdateUserFullnameUseCase(userRepository = get()) }
    single { UpdateUserPhoneUseCase(userRepository = get()) }
    single { DeleteRemoteUserUseCase(userRepository = get()) }
}
