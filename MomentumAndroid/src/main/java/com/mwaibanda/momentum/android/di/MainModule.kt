package com.mwaibanda.momentum.android.di

import com.mwaibanda.momentum.android.core.utils.AppReviewRequester
import com.mwaibanda.momentum.android.core.utils.DefaultDispatches
import com.mwaibanda.momentum.android.core.utils.DispatchersProvider
import com.mwaibanda.momentum.controller.AuthControllerImpl
import com.mwaibanda.momentum.data.db.DatabaseDriverFactory
import com.mwaibanda.momentum.domain.controller.AuthController
import io.github.mwaibanda.authentication.di.Authentication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainModule = module {
    single { DatabaseDriverFactory(androidContext()) }
    single<DispatchersProvider> { DefaultDispatches() }
    single { AppReviewRequester(get()) }
    single <AuthController>{ AuthControllerImpl(Authentication.controller) }
}