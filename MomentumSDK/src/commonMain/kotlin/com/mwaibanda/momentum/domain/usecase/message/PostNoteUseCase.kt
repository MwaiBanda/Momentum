package com.mwaibanda.momentum.domain.usecase.message

import com.mwaibanda.momentum.domain.models.NoteRequest
import com.mwaibanda.momentum.domain.repository.MessageRepository
import com.mwaibanda.momentum.utils.CommonFlow
import com.mwaibanda.momentum.utils.DataResponse
import com.mwaibanda.momentum.utils.Result
import com.mwaibanda.momentum.utils.asCommonFlow
import kotlinx.coroutines.flow.flow
import kotlin.native.ObjCName

class PostNoteUseCase(
    private val messageRepository: MessageRepository
) {
    @ObjCName("execute")
    suspend operator fun invoke(note: NoteRequest): CommonFlow<Result<NoteRequest>> = flow {
        emit(Result.Loading())
        when(val res = messageRepository.addNoteToPassage(note)) {
            is DataResponse.Failure -> emit(Result.Error(res.message))
            is DataResponse.Success -> emit(Result.Data(res.data))
        }
    }.asCommonFlow()
}