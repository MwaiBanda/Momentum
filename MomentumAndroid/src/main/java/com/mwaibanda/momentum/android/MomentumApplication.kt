package com.mwaibanda.momentum.android

import android.app.Application
import com.mwaibanda.momentum.android.di.controllerModule
import com.mwaibanda.momentum.android.di.viewModelModule
import com.mwaibanda.momentum.di.initKoin
import org.koin.android.ext.koin.androidContext

class MomentumApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MomentumApplication)
            modules(
                controllerModule,
                viewModelModule
            )
        }
    }
}