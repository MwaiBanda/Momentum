package com.mwaibanda.momentum.domain.usecase.localDefaults

import com.mwaibanda.momentum.domain.repository.LocalDefaultsRepository

class GetBooleanUseCase (
    private val localDefaultsRepository: LocalDefaultsRepository
) {
    operator fun invoke(key: String, onRetrieval: (Boolean) -> Unit){
        onRetrieval(localDefaultsRepository.getBoolean(key = key))
    }
}