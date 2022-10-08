package com.mwaibanda.momentum.android

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.mwaibanda.momentum.android.di.mainModule
import com.mwaibanda.momentum.android.di.viewModelModule
import com.mwaibanda.momentum.di.controllerModule
import com.mwaibanda.momentum.di.initKoin
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.koin.android.ext.koin.androidContext

class MomentumApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        Firebase.firestore.android.firestoreSettings = FirebaseFirestoreSettings.Builder(Firebase.firestore.android.firestoreSettings)
            .setPersistenceEnabled(false)
            .build()
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