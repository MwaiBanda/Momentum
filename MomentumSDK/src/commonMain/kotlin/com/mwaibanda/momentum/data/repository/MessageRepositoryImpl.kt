package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MessageResponse
import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.domain.models.Note
import com.mwaibanda.momentum.domain.models.NoteRequest
import com.mwaibanda.momentum.domain.repository.MessageRepository
import com.mwaibanda.momentum.domain.usecase.cache.GetItemUseCase
import com.mwaibanda.momentum.domain.usecase.cache.SetItemUseCase
import com.mwaibanda.momentum.utils.DataResponse
import com.mwaibanda.momentum.utils.MultiplatformConstants.MESSAGE_KEY
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import com.mwaibanda.momentum.data.messageDTO.MessageDTO as MessageContainer

class MessageRepositoryImpl(
    private val httpClient: HttpClient,
    private val getItemUseCase: GetItemUseCase<MessageResponse>,
    private val setItemUseCase: SetItemUseCase<MessageResponse>,
): MomentumBase(), MessageRepository {
    override suspend fun fetchAllMessages(userId: String): DataResponse<MessageResponse> {
        val cacheMessages = getItemUseCase(MESSAGE_KEY).orEmpty()
        if (cacheMessages.isNotEmpty()) {
            return DataResponse.Success(cacheMessages)
        }
         return try {
            val messages = httpClient.get {
                momentumAPI("$MESSAGE_ENDPOINT/$userId")
            }.body<MessageContainer>().data
             if (messages.isNotEmpty()) {
                 setItemUseCase(MESSAGE_KEY, messages)
             }
             DataResponse.Success(messages)
        } catch (e: Exception) {
           return DataResponse.Failure(e.message.toString())
        }
    }

    override suspend fun addNoteToPassage(request: NoteRequest): DataResponse<NoteRequest> {
        return try {
            val response: NoteRequest = httpClient.post {
                momentumAPI(NOTES_ENDPOINT, request)
            }.body()
            DataResponse.Success(response)
        } catch (e: Exception) {
            DataResponse.Failure(e.message.toString())
        }
    }

    override suspend fun updateNote(note: Note.UserNote): DataResponse<Note> {
        return try {
            val response: Note = httpClient.put {
                momentumAPI(NOTES_ENDPOINT, note)
            }.body()
            DataResponse.Success(response)
        } catch (e: Exception) {
            DataResponse.Failure(e.message.toString())
        }
    }

    override suspend fun deleteNote(userId: String): DataResponse<Note> {
        return try {
            val response: Note = httpClient.delete {
                momentumAPI("$NOTES_ENDPOINT/$userId")
            }.body()
            DataResponse.Success(response)
        } catch (e: Exception) {
            DataResponse.Failure(e.message.toString())
        }
    }


}