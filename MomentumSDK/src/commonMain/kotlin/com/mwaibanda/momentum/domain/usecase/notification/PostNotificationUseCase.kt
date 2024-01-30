package com.mwaibanda.momentum.domain.usecase.notification

import com.mwaibanda.momentum.domain.models.Notification
import com.mwaibanda.momentum.domain.repository.NotificationRepository
import com.mwaibanda.momentum.utils.DataResponse

class PostNotificationUseCase(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(notification: Notification, onCompletion: (DataResponse<Notification>) -> Unit){
        onCompletion(notificationRepository.postNotification(notification))
    }
}