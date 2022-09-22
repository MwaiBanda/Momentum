package com.mwaibanda.momentum.di

import com.mwaibanda.momentum.domain.usecase.auth.*
import com.mwaibanda.momentum.domain.usecase.cache.GetItemUseCase
import com.mwaibanda.momentum.domain.usecase.cache.SetItemUseCase
import com.mwaibanda.momentum.domain.usecase.localDefaults.*
import com.mwaibanda.momentum.domain.usecase.payment.CheckoutUseCase
import com.mwaibanda.momentum.domain.usecase.payment.PostTransactionInfoUseCase
import com.mwaibanda.momentum.domain.usecase.sermon.GetSermonsUseCase
import com.mwaibanda.momentum.domain.usecase.user.*
import org.koin.dsl.module

val useCasesModule = module {
    /**
     * @Payment - Use-cases
     */
    single { CheckoutUseCase(paymentRepository = get()) }
    single { PostTransactionInfoUseCase(paymentRepository = get()) }
    /**
     * @Auth - Use-cases
     */
    single { SignInWithEmailUseCase(authRepository = get()) }
    single { SignUpWithEmailUseCase(authRepository = get()) }
    single { SignInAsGuestUseCase(authRepository = get()) }
    single { IsUserSignedInUseCase(authRepository = get()) }
    single { GetCurrentUserUseCase(authRepository = get()) }
    single { DeleteUserUseCase(authRepository = get()) }
    single { SignOutUseCase(authRepository = get()) }
    /**
     * @User - Use-cases
     */
    single { PostUserUseCase(userRepository = get()) }
    single { GetUserUseCase(userRepository = get()) }
    single { UpdateUserEmailUseCase(userRepository = get()) }
    single { UpdateUserFullnameUseCase(userRepository = get()) }
    single { UpdateUserPhoneUseCase(userRepository = get()) }
    single { DeleteRemoteUserUseCase(userRepository = get()) }
    /**
     * @LocalDefaults - Use-cases
     */
    single { SetStringUseCase(localDefaultsRepository = get()) }
    single { GetStringUseCase(localDefaultsRepository = get()) }
    single { SetIntUseCase(localDefaultsRepository = get()) }
    single { GetIntUseCase(localDefaultsRepository = get()) }
    single { SetBooleanUseCase(localDefaultsRepository = get()) }
    single { GetBooleanUseCase(localDefaultsRepository = get()) }
    /**
     * @Sermon - Use-cases
     */
    single { GetSermonsUseCase(sermonRepository = get()) }
    /**
     * @Cache - Use-cases
     */
    single { GetItemUseCase<Any>(cacheRepository = get()) }
    single { SetItemUseCase<Any>(cacheRepository = get()) }
}

