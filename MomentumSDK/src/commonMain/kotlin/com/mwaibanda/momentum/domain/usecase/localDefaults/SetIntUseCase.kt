package com.mwaibanda.momentum.domain.usecase.localDefaults

import com.mwaibanda.momentum.domain.repository.LocalDefaultsRepository

class SetIntUseCase (
    private val localDefaultsRepository: LocalDefaultsRepository
) {
    operator fun invoke(key: String, value: Int){
        localDefaultsRepository.setInt(key = key, value = value)
    }
}