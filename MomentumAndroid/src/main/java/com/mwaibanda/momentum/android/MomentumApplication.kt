package com.mwaibanda.momentum.android

import android.app.Application
import com.google.firebase.FirebaseApp
import com.mwaibanda.momentum.android.di.mainModule
import com.mwaibanda.momentum.android.di.viewModelModule
import com.mwaibanda.momentum.di.*
import com.mwaibanda.momentum.di.initKoin
import org.koin.android.ext.koin.androidContext
import java.util.logging.Level
import java.util.logging.Logger

class MomentumApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        initKoin {
            androidContext(this@MomentumApplication)
            modules(
                mainModule,
                controllerModule,
                viewModelModule
            )
        }
    }
}