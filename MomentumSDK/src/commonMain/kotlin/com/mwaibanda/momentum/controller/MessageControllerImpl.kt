package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.data.repository.MessageRepositoryImpl
import com.mwaibanda.momentum.domain.controller.MessageController
import com.mwaibanda.momentum.domain.models.Message
import com.mwaibanda.momentum.domain.usecase.cache.InvalidateItemsUseCase
import com.mwaibanda.momentum.domain.usecase.message.GetAllMessagesUseCase
import com.mwaibanda.momentum.utils.DataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MessageControllerImpl: MessageController, KoinComponent {
    private val getAllMessagesUseCase: GetAllMessagesUseCase by inject()
    private val invalidateItemsUseCase: InvalidateItemsUseCase by inject()
    private val scope: CoroutineScope = MainScope()
    override fun getAllMessages(userId: String, onCompletion: (DataResponse<List<Message>>) -> Unit) {
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