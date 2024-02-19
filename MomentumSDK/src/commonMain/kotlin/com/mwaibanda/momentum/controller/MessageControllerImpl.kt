package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.data.repository.MessageRepositoryImpl
import com.mwaibanda.momentum.domain.controller.MessageController
import com.mwaibanda.momentum.domain.models.MessageGroup
import com.mwaibanda.momentum.domain.usecase.cache.InvalidateItemsUseCase
import com.mwaibanda.momentum.domain.usecase.message.GetAllMessagesUseCase
import com.mwaibanda.momentum.utils.DataResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MessageControllerImpl: MessageController, KoinComponent {
    private val getAllMessagesUseCase: GetAllMessagesUseCase by inject()
    private val invalidateItemsUseCase: InvalidateItemsUseCase by inject()
    override suspend fun getAllMessages(userId: String, onCompletion: (DataResponse<List<MessageGroup>>) -> Unit) {
        getAllMessagesUseCase(userId = userId){
            onCompletion(it)
        }
    }

    override fun clearMessagesCache() {
        invalidateItemsUseCase(MessageRepositoryImpl.MESSAGE_KEY)
    }
}