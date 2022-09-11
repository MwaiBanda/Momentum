package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.domain.controller.LocalDefaultsController
import com.mwaibanda.momentum.domain.usecase.localDefaults.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LocalDefaultsControllerImpl: LocalDefaultsController, KoinComponent {
    val setStringUseCase: SetStringUseCase by inject()
    val getStringUseCase: GetStringUseCase by inject()
    val getIntUseCase: GetIntUseCase by inject()
    val setIntUseCase: SetIntUseCase by inject()
    val getBooleanUseCase: GetBooleanUseCase by inject()
    val setBooleanUseCase: SetBooleanUseCase by inject()
    override fun setString(key: String, value: String) {
        setStringUseCase(key = key, value = value)
    }

    override fun getString(key: String, onRetrieval: (String) -> Unit) {
        getStringUseCase(key = key, onRetrieval = onRetrieval)
    }

    override fun setInt(key: String, value: Int) {
        setIntUseCase(key = key, value = value)
    }

    override fun getInt(key: String, onRetrieval: (Int) -> Unit) {
        getIntUseCase(key = key, onRetrieval = onRetrieval)
    }

    override fun setBoolean(key: String, value: Boolean) {
        setBooleanUseCase(key = key, value = value)
    }

    override fun getBoolean(key: String, onRetrieval: (Boolean) -> Unit) {
        getBooleanUseCase(key = key, onRetrieval = onRetrieval)
    }
}