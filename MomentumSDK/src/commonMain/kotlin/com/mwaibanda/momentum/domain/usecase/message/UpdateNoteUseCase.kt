package com.mwaibanda.momentum.domain.usecase.message

import com.mwaibanda.momentum.domain.models.Note
import com.mwaibanda.momentum.domain.repository.MessageRepository
import com.mwaibanda.momentum.utils.CommonFlow
import com.mwaibanda.momentum.utils.DataResponse
import com.mwaibanda.momentum.utils.Result
import com.mwaibanda.momentum.utils.asCommonFlow
import kotlinx.coroutines.flow.flow
import kotlin.native.ObjCName

class UpdateNoteUseCase(
    private val messageRepository: MessageRepository
) {
    @ObjCName("update")
    suspend operator fun invoke(note: Note.UserNote): CommonFlow<Result<Note>> = flow {
        emit(Result.Loading())
        when(val res = messageRepository.updateNote(note)) {
            is DataResponse.Failure -> emit(Result.Error(res.message))
            is DataResponse.Success -> emit(Result.Data(res.data))
        }
    }.asCommonFlow()
}