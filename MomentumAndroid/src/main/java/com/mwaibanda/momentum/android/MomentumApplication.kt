package com.mwaibanda.momentum.android

import android.app.Application
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.mwaibanda.momentum.android.di.mainModule
import com.mwaibanda.momentum.android.di.viewModelModule
import com.mwaibanda.momentum.di.controllerModule
import com.mwaibanda.momentum.di.initKoin
import com.mwaibanda.momentum.utils.MultiplatformConstants
import io.github.mwaibanda.authentication.di.Authentication
import io.github.mwaibanda.authentication.di.initialize
import org.koin.android.ext.koin.androidContext

class MomentumApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Authentication.initialize(this)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e(TAG, "Fetching FCM registration token failed", task.exception)
            }
            val token = task.result
            Log.e(TAG, token)
            FirebaseMessaging.getInstance().subscribeToTopic(MultiplatformConstants.ALL_USERS_TOPIC).addOnCompleteListener {
                if (!it.isSuccessful) {
                    Log.e(TAG, "Subscribing to Topic(${MultiplatformConstants.ALL_USERS_TOPIC}) failed", task.exception)
                } else {
                    Log.e(TAG, "Subscribed to Topic(${MultiplatformConstants.ALL_USERS_TOPIC})")
                    FirebaseMessaging.getInstance().subscribeToTopic(MultiplatformConstants.ALL_ANDROID_USERS_TOPIC).addOnCompleteListener {
                        if (!it.isSuccessful) {
                            Log.e(TAG, "Subscribing to Topic(${MultiplatformConstants.ALL_ANDROID_USERS_TOPIC}) failed", task.exception)
                        } else {
                            Log.e(TAG, "Subscribed to Topic(${MultiplatformConstants.ALL_ANDROID_USERS_TOPIC})")
                        }
                    }
                }
            }
        })
        initKoin {
            androidContext(this@MomentumApplication)
            modules(
                mainModule,
                controllerModule,
                viewModelModule
            )
        }

    }

    companion object {
        private const val TAG = "MomentumApplication"
        const val DEFAULT_CHANNEL_ID = "Momentum-1001"
        const val DEFAULT_CHANNEL = "Momentum Notifications"
    }
}