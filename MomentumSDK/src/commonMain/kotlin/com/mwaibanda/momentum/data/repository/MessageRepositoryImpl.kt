package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.domain.models.Message
import com.mwaibanda.momentum.domain.models.MessageResponse
import com.mwaibanda.momentum.domain.repository.MessageRepository
import com.mwaibanda.momentum.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MessageRepositoryImpl(
    private val httpClient: HttpClient
): MomentumBase(), MessageRepository {
    override suspend fun fetchAllMessages(userId: String): Result<List<Message>> {
        return try {
            val messages = httpClient.get {
                momentumAPI("$MESSAGE_ENDPOINT/$userId")
            }.body<MessageResponse>().data
            Result.Success(messages)
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }
}