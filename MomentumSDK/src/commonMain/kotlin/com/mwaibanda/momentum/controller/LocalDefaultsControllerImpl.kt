package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.domain.controller.LocalDefaultsController
import com.mwaibanda.momentum.domain.usecase.localDefaults.GetStringUseCase
import com.mwaibanda.momentum.domain.usecase.localDefaults.SetStringUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LocalDefaultsControllerImpl: LocalDefaultsController, KoinComponent {
    val setStringUseCase: SetStringUseCase by inject()
    val getStringUseCase: GetStringUseCase by inject()

    override fun setString(key: String, value: String) {
        setStringUseCase(key = key, value = value)
    }

    override fun getString(key: String, onRetrieval: (String) -> Unit) {
        getStringUseCase(key = key, onRetrieval = onRetrieval)
    }
}