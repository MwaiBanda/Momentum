package com.mwaibanda.momentum.di

import com.mwaibanda.momentum.domain.usecase.message.PostNoteUseCase
import com.mwaibanda.momentum.domain.usecase.message.UpdateNoteUseCase
import io.github.mwaibanda.authentication.di.Authentication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object Momentum: KoinComponent {
    val postNoteUseCase: PostNoteUseCase by inject()
    val updateNoteUseCase: UpdateNoteUseCase by inject()
    val auth = Authentication
}