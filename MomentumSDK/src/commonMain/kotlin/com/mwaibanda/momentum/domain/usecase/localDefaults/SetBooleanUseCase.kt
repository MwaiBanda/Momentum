package com.mwaibanda.momentum.domain.usecase.localDefaults

import com.mwaibanda.momentum.domain.repository.LocalDefaultsRepository

class SetBooleanUseCase(
    private val localDefaultsRepository: LocalDefaultsRepository
) {
    operator fun invoke(key: String, value: Boolean){
        localDefaultsRepository.setBoolean(key = key, value = value)
    }
}