package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.domain.models.Message
import com.mwaibanda.momentum.utils.DataResponse

interface MessageController {
    fun  getAllMessages(userId: String, onCompletion: (DataResponse<List<Message>>) -> Unit)

    fun clearMessagesCache()

}