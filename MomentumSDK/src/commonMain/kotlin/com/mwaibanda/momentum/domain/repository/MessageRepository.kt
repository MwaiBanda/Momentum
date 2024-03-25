package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.Message
import com.mwaibanda.momentum.domain.models.Note
import com.mwaibanda.momentum.domain.models.NoteRequest
import com.mwaibanda.momentum.utils.DataResponse

interface MessageRepository {
    suspend fun fetchAllMessages(userId: String): DataResponse<List<Message>>
    suspend fun addNoteToPassage(request: NoteRequest): DataResponse<NoteRequest>
    suspend fun updateNote(note: Note.UserNote): DataResponse<Note>
    suspend fun deleteNote(userId: String): DataResponse<Note>

}