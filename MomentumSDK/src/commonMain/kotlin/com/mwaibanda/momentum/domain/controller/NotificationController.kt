package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.domain.models.Notification
import com.mwaibanda.momentum.utils.DataResponse

interface NotificationController {
    fun sendNotification(notification: Notification, onCompletion: (DataResponse<Notification>) -> Unit)
}