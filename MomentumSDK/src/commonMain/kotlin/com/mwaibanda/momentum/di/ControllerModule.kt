package com.mwaibanda.momentum.di

import com.mwaibanda.momentum.controller.AuthControllerImpl
import com.mwaibanda.momentum.controller.BillingAddressControllerImpl
import com.mwaibanda.momentum.controller.EventControllerImpl
import com.mwaibanda.momentum.controller.LocalDefaultsControllerImpl
import com.mwaibanda.momentum.controller.MealControllerImpl
import com.mwaibanda.momentum.controller.NotificationControllerImpl
import com.mwaibanda.momentum.controller.PaymentControllerImpl
import com.mwaibanda.momentum.controller.SermonControllerImpl
import com.mwaibanda.momentum.controller.TransactionControllerImpl
import com.mwaibanda.momentum.controller.UserControllerImpl
import com.mwaibanda.momentum.domain.controller.AuthController
import com.mwaibanda.momentum.domain.controller.BillingAddressController
import com.mwaibanda.momentum.domain.controller.EventController
import com.mwaibanda.momentum.domain.controller.LocalDefaultsController
import com.mwaibanda.momentum.domain.controller.MealController
import com.mwaibanda.momentum.domain.controller.MessageController
import com.mwaibanda.momentum.domain.controller.NotificationController
import com.mwaibanda.momentum.domain.controller.PaymentController
import com.mwaibanda.momentum.domain.controller.SermonController
import com.mwaibanda.momentum.domain.controller.TransactionController
import com.mwaibanda.momentum.domain.controller.UserController
import org.koin.dsl.module

val controllerModule = module {
    single<PaymentController> { PaymentControllerImpl() }
    single<TransactionController> { TransactionControllerImpl(driverFactory = get()) }
    single<UserController> { UserControllerImpl(driverFactory = get()) }
    single<BillingAddressController>{ BillingAddressControllerImpl(driverFactory = get()) }
    single<AuthController> { AuthControllerImpl(controller = get()) }
    single<LocalDefaultsController> { LocalDefaultsControllerImpl() }
    single<SermonController> { SermonControllerImpl(driverFactory = get()) }
    single<MealController> { MealControllerImpl() }
    single<NotificationController> { NotificationControllerImpl() }
    single<EventController> { EventControllerImpl() }
}
