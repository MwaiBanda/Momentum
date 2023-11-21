package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.Message
import com.mwaibanda.momentum.utils.Result

interface MessageRepository {
    suspend fun fetchAllMessages(userId: String): Result<List<Message>>
}