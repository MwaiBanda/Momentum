package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.Notification
import com.mwaibanda.momentum.utils.Result

interface NotificationRepository {
    suspend fun postNotification(notification: Notification): Result<Notification>
}