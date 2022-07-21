package com.mwaibanda.momentum.di

import com.mwaibanda.momentum.controller.AuthControllerImpl
import com.mwaibanda.momentum.controller.PaymentControllerImpl
import com.mwaibanda.momentum.controller.TransactionControllerImpl
import com.mwaibanda.momentum.data.repository.AuthRepositoryImpl
import com.mwaibanda.momentum.domain.controller.AuthController
import com.mwaibanda.momentum.domain.controller.PaymentController
import com.mwaibanda.momentum.domain.controller.TransactionController
import com.mwaibanda.momentum.domain.repository.AuthRepository
import dev.gitlive.firebase.auth.FirebaseAuth
import org.koin.dsl.module

val controllerModule = module {
    single<PaymentController> { PaymentControllerImpl() }
    single<TransactionController> { TransactionControllerImpl(driverFactory = get()) }
    single<AuthController> { AuthControllerImpl() }
}
