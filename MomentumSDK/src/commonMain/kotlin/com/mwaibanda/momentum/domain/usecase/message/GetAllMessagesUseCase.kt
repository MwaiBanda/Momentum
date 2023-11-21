package com.mwaibanda.momentum.domain.usecase.message

import com.mwaibanda.momentum.domain.models.Message
import com.mwaibanda.momentum.domain.repository.MessageRepository
import com.mwaibanda.momentum.utils.Result

class GetAllMessagesUseCase(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(userId: String, onCompletion: (Result<List<Message>>) -> Unit) {
        onCompletion(messageRepository.fetchAllMessages(userId))
    }
}