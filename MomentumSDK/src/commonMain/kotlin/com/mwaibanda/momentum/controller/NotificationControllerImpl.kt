package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.domain.controller.NotificationController
import com.mwaibanda.momentum.domain.models.Notification
import com.mwaibanda.momentum.domain.usecase.notification.PostNotificationUseCase
import com.mwaibanda.momentum.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationControllerImpl: NotificationController, KoinComponent {
    private val postNotificationUseCase: PostNotificationUseCase by inject()
    private val completableJob = SupervisorJob()
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default + completableJob)

    override fun sendNotification(
        notification: Notification,
        onCompletion: (Result<Notification>) -> Unit,
    ) {
        scope.launch {
            postNotificationUseCase.invoke(
                notification = notification,
                onCompletion = onCompletion
            )
        }
    }
}