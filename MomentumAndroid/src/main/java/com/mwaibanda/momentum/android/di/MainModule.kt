package com.mwaibanda.momentum.android.di

import com.mwaibanda.momentum.data.db.DatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainModule = module {
    single { DatabaseDriverFactory(androidContext()) }
}