package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.domain.models.MessageGroup
import com.mwaibanda.momentum.utils.DataResponse

interface MessageController {
    suspend fun getAllMessages(userId: String, onCompletion: (DataResponse<List<MessageGroup>>) -> Unit)

    fun clearMessagesCache()

}