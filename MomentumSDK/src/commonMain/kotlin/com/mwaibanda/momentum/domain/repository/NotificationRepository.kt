package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.Notification
import com.mwaibanda.momentum.utils.DataResponse

interface NotificationRepository {
    suspend fun postNotification(notification: Notification): DataResponse<Notification>
}