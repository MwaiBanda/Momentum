package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MessageResponse
import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.domain.repository.MessageRepository
import com.mwaibanda.momentum.domain.usecase.cache.GetItemUseCase
import com.mwaibanda.momentum.domain.usecase.cache.SetItemUseCase
import com.mwaibanda.momentum.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import com.mwaibanda.momentum.data.messageDTO.MessageDTO as MessageContainer

class MessageRepositoryImpl(
    private val httpClient: HttpClient,
    private val getItemUseCase: GetItemUseCase<MessageResponse>,
    private val setItemUseCase: SetItemUseCase<MessageResponse>,
): MomentumBase(), MessageRepository {
    override suspend fun fetchAllMessages(userId: String): Result<MessageResponse> {
        val cacheMessages = getItemUseCase(MESSAGE_KEY).orEmpty()
        if (cacheMessages.isNotEmpty()) {
            return Result.Success(cacheMessages)
        }
         try {
            val messages = httpClient.get {
                momentumAPI("$MESSAGE_ENDPOINT/$userId")
            }.body<MessageContainer>().data
            setItemUseCase(MESSAGE_KEY, messages)
        } catch (e: Exception) {
           return Result.Failure(e.message.toString())
        }

        val newlyCacheMessages = getItemUseCase(MESSAGE_KEY).orEmpty()
        return Result.Success(newlyCacheMessages)
    }

    companion object {
        const val MESSAGE_KEY = "messages"
    }
}