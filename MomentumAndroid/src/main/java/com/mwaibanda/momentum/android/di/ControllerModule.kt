package com.mwaibanda.momentum.android.di

import com.mwaibanda.momentum.controller.PaymentControllerImpl
import com.mwaibanda.momentum.domain.controller.PaymentController
import org.koin.dsl.module


val controllerModule = module {
    single<PaymentController> { PaymentControllerImpl()  }
}