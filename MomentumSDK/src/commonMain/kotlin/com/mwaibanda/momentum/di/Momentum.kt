package com.mwaibanda.momentum.di

import com.mwaibanda.momentum.domain.usecase.message.MessageUseCases
import io.github.mwaibanda.authentication.di.Authentication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object Momentum: KoinComponent {
    val messageUseCases: MessageUseCases by inject()
    val auth = Authentication
}