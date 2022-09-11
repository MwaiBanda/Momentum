package com.mwaibanda.momentum.android.di

import com.mwaibanda.momentum.android.core.utils.AppReviewRequester
import com.mwaibanda.momentum.android.core.utils.DefaultDispatches
import com.mwaibanda.momentum.android.core.utils.DispatchersProvider
import com.mwaibanda.momentum.data.db.DatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainModule = module {
    single { DatabaseDriverFactory(androidContext()) }
    single<DispatchersProvider> { DefaultDispatches() }
    single { AppReviewRequester(get()) }
}