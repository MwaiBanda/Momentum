package com.mwaibanda.momentum.android.core.utils

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mwaibanda.momentum.android.MomentumApplication
import com.mwaibanda.momentum.android.R
import kotlin.random.Random

class MessagingService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.e("MessagingService", "Refreshed token: $token")
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val notification =  NotificationCompat.Builder(this, MomentumApplication.DEFAULT_CHANNEL_ID)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(applicationContext)
                .notify(Random(1000).nextInt(), notification)
        }
    }
}