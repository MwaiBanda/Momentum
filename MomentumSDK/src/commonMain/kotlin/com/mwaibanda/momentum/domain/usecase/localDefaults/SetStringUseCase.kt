package com.mwaibanda.momentum.domain.usecase.localDefaults

import com.mwaibanda.momentum.domain.repository.LocalDefaultsRepository

class SetStringUseCase(
    private val localDefaultsRepository: LocalDefaultsRepository
) {
    operator fun invoke(key: String, value: String){
        localDefaultsRepository.setString(key = key, value = value)
    }
}