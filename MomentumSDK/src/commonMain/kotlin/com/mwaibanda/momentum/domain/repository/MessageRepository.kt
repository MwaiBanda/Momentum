package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.Message
import com.mwaibanda.momentum.domain.models.Note
import com.mwaibanda.momentum.domain.models.NoteRequest
import com.mwaibanda.momentum.utils.Result

interface MessageRepository {
    suspend fun fetchAllMessages(userId: String): Result<List<Message>>
    suspend fun addNoteToPassage(request: NoteRequest): Result<NoteRequest>
    suspend fun updateNote(note: Note.UserNote): Result<Note>

}