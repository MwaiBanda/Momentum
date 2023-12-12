package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.domain.models.Message
import com.mwaibanda.momentum.utils.Result

interface MessageController {
    fun  getAllMessages(userId: String, onCompletion: (Result<List<Message>>) -> Unit)

    fun clearMessagesCache()

}