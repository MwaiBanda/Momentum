package com.mwaibanda.momentum.di

import com.mwaibanda.momentum.controller.PaymentControllerImpl
import com.mwaibanda.momentum.controller.TransactionControllerImpl
import com.mwaibanda.momentum.domain.controller.PaymentController
import com.mwaibanda.momentum.domain.controller.TransactionController
import org.koin.dsl.module

val controllerModule = module {
    single<PaymentController>{  PaymentControllerImpl(checkoutUseCase = get()) }
    single<TransactionController> { TransactionControllerImpl(driverFactory = get()) }
}
