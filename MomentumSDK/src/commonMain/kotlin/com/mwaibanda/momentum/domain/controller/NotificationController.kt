package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.domain.models.Notification
import com.mwaibanda.momentum.utils.Result

interface NotificationController {
    fun sendNotification(notification: Notification, onCompletion: (Result<Notification>) -> Unit)
}