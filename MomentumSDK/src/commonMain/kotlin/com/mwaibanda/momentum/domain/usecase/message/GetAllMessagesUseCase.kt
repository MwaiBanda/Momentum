package com.mwaibanda.momentum.domain.usecase.message

import com.mwaibanda.momentum.domain.models.MessageGroup
import com.mwaibanda.momentum.domain.repository.MessageRepository
import com.mwaibanda.momentum.utils.DataResponse

class GetAllMessagesUseCase(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(userId: String, onCompletion: (DataResponse<List<MessageGroup>>) -> Unit) {
        when(val res = messageRepository.fetchAllMessages(userId)) {
            is DataResponse.Failure -> onCompletion(DataResponse.Failure(message = res.message ?: "[GetAllMessagesUseCase]: Unknown Error"))
            is DataResponse.Success -> {
                onCompletion(
                    DataResponse.Success(
                        data = res.data?.groupBy { message ->
                            message.series
                        }?.map { messageEntry ->
                            MessageGroup(
                                series = messageEntry.key.split(" ").joinToString(" ") {
                                    it.lowercase().replaceRange(0, 1, it[0].uppercase())
                                },
                                messages = messageEntry.value
                            )
                        } ?: emptyList()
                    )
                )
            }
        }
    }
}