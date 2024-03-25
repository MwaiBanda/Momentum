package com.mwaibanda.momentum.domain.usecase.message

import com.mwaibanda.momentum.domain.models.MessageGroup
import com.mwaibanda.momentum.domain.repository.MessageRepository
import com.mwaibanda.momentum.utils.CommonFlow
import com.mwaibanda.momentum.utils.DataResponse
import com.mwaibanda.momentum.utils.Result
import com.mwaibanda.momentum.utils.asCommonFlow
import kotlinx.coroutines.flow.flow
import kotlin.native.ObjCName

class GetAllMessagesUseCase(
    private val messageRepository: MessageRepository
) {
    @ObjCName("execute")
    suspend operator fun invoke(userId: String): CommonFlow<Result<List<MessageGroup>>> = flow {
        emit(Result.Loading())
        when(val res = messageRepository.fetchAllMessages(userId)) {
            is DataResponse.Failure -> emit(Result.Error(message = res.message ?: "[GetAllMessagesUseCase]: Unknown Error"))
            is DataResponse.Success -> emit(
                    Result.Data(
                        data = res.data?.groupBy { message ->
                            message.series
                        }?.map { messageEntry ->
                            MessageGroup(
                                series = messageEntry.key,
                                messages = messageEntry.value
                            )
                        } ?: emptyList()
                    )
                )

        }
    }.asCommonFlow()
}