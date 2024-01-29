package com.mwaibanda.momentum.domain.usecase.message

import com.mwaibanda.momentum.domain.models.NoteRequest
import com.mwaibanda.momentum.domain.repository.MessageRepository
import com.mwaibanda.momentum.utils.Result
import com.mwaibanda.momentum.utils.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostNoteUserCase(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(note: NoteRequest): Flow<State<NoteRequest>> = flow {
        emit(State.Loading())
        when(val res = messageRepository.addNoteToPassage(note)) {
            is Result.Failure -> emit(State.Error(res.message))
            is Result.Success -> emit(State.Data(res.data))
        }
    }
}