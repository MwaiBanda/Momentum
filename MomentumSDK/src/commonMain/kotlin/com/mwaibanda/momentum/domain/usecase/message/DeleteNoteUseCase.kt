package com.mwaibanda.momentum.domain.usecase.message

import com.mwaibanda.momentum.domain.models.Note
import com.mwaibanda.momentum.domain.repository.MessageRepository
import com.mwaibanda.momentum.utils.CommonFlow
import com.mwaibanda.momentum.utils.DataResponse
import com.mwaibanda.momentum.utils.Result
import com.mwaibanda.momentum.utils.asCommonFlow
import kotlinx.coroutines.flow.flow
import kotlin.native.ObjCName

class DeleteNoteUseCase(
    private val messageRepository: MessageRepository
) {
    @ObjCName("execute")
    suspend operator fun invoke(noteId: String): CommonFlow<Result<Note>> = flow {
        emit(Result.Loading())
        when(val res = messageRepository.deleteNote(noteId)) {
            is DataResponse.Failure -> emit(Result.Error(res.message))
            is DataResponse.Success -> emit(Result.Data(res.data))
        }
    }.asCommonFlow()
}