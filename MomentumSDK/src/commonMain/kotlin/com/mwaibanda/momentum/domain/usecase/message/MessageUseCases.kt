package com.mwaibanda.momentum.domain.usecase.message

import com.mwaibanda.momentum.domain.usecase.cache.InvalidateItemsUseCase

data class MessageUseCases(
    val create: PostNoteUseCase,
    val read: GetAllMessagesUseCase,
    val update: UpdateNoteUseCase,
    val delete: DeleteNoteUseCase,
    val clearCache: InvalidateItemsUseCase
)
