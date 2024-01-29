package com.mwaibanda.momentum.domain.usecase.message

import com.mwaibanda.momentum.domain.models.Note
import com.mwaibanda.momentum.domain.repository.MessageRepository
import com.mwaibanda.momentum.utils.Result
import com.mwaibanda.momentum.utils.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateNoteUseCase(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(note: Note.UserNote): Flow<State<Note>> = flow {
        emit(State.Loading())
        when(val res = messageRepository.updateNote(note)) {
            is Result.Failure -> emit(State.Error(res.message))
            is Result.Success -> emit(State.Data(res.data))
        }
    }
}