package com.mwaibanda.momentum.di

import com.mwaibanda.momentum.controller.*
import com.mwaibanda.momentum.data.repository.AuthRepositoryImpl
import com.mwaibanda.momentum.domain.controller.*
import com.mwaibanda.momentum.domain.repository.AuthRepository
import dev.gitlive.firebase.auth.FirebaseAuth
import org.koin.dsl.module

val controllerModule = module {
    single<PaymentController> { PaymentControllerImpl() }
    single<TransactionController> { TransactionControllerImpl(driverFactory = get()) }
    single<UserController> { UserControllerImpl(driverFactory = get()) }
    single<BillingAddressController>{ BillingAddressControllerImpl(driverFactory = get()) }
    single<AuthController> { AuthControllerImpl() }
}
