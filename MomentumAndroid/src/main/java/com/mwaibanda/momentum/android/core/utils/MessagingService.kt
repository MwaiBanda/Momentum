package com.mwaibanda.momentum.android.core.utils

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class MessagingService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.e("MessagingService", "Refreshed token: $token")
        super.onNewToken(token)
    }
}