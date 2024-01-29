package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MessageResponse
import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.domain.models.Note
import com.mwaibanda.momentum.domain.models.NoteRequest
import com.mwaibanda.momentum.domain.repository.MessageRepository
import com.mwaibanda.momentum.domain.usecase.cache.GetItemUseCase
import com.mwaibanda.momentum.domain.usecase.cache.SetItemUseCase
import com.mwaibanda.momentum.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import kotlinx.coroutines.delay
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
             if (messages.isNotEmpty()) {
                 setItemUseCase(MESSAGE_KEY, messages)
             } else {
                 delay(30000)
                 fetchAllMessages(userId = userId)
             }
        } catch (e: Exception) {
           return Result.Failure(e.message.toString())
        }

        val newlyCacheMessages = getItemUseCase(MESSAGE_KEY).orEmpty()
        return Result.Success(newlyCacheMessages)
    }

    override suspend fun addNoteToPassage(request: NoteRequest): Result<NoteRequest> {
        return try {
            val response: NoteRequest = httpClient.post {
                momentumAPI(NOTES_ENDPOINT, request)
            }.body()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }

    override suspend fun updateNote(note: Note.UserNote): Result<Note> {
        return try {
            val response: Note = httpClient.put {
                momentumAPI(NOTES_ENDPOINT, note)
            }.body()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }

    companion object {
        const val MESSAGE_KEY = "messages"
    }
}