package com.mwaibanda.momentum.di

import com.mwaibanda.momentum.controller.*
import com.mwaibanda.momentum.domain.controller.*
import org.koin.dsl.module

val controllerModule = module {
    single<PaymentController> { PaymentControllerImpl() }
    single<TransactionController> { TransactionControllerImpl(driverFactory = get()) }
    single<UserController> { UserControllerImpl(driverFactory = get()) }
    single<BillingAddressController>{ BillingAddressControllerImpl(driverFactory = get()) }
    single<AuthController> { AuthControllerImpl() }
    single<LocalDefaultsController> { LocalDefaultsControllerImpl() }
    single<SermonController> { SermonControllerImpl(driverFactory = get()) }
}
