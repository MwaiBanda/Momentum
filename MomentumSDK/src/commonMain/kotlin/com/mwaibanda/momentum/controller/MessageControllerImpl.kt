package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.data.repository.MessageRepositoryImpl
import com.mwaibanda.momentum.domain.controller.MessageController
import com.mwaibanda.momentum.domain.models.Message
import com.mwaibanda.momentum.domain.usecase.cache.InvalidateItemsUseCase
import com.mwaibanda.momentum.domain.usecase.message.GetAllMessagesUseCase
import com.mwaibanda.momentum.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MessageControllerImpl: MessageController, KoinComponent {
    private val getAllMessagesUseCase: GetAllMessagesUseCase by inject()
    private val invalidateItemsUseCase: InvalidateItemsUseCase by inject()
    private val completableJob = SupervisorJob()
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default + completableJob)

    override fun getAllMessages(userId: String, onCompletion: (Result<List<Message>>) -> Unit) {
        scope.launch {
            getAllMessagesUseCase(
                userId = userId,
                onCompletion = onCompletion
            )
        }
    }

    override fun clearMessagesCache() {
        invalidateItemsUseCase(MessageRepositoryImpl.MESSAGE_KEY)
    }
}