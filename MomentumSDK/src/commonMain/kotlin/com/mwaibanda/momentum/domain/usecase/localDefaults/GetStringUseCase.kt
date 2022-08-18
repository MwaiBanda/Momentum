package com.mwaibanda.momentum.domain.usecase.localDefaults

import com.mwaibanda.momentum.domain.repository.LocalDefaultsRepository

class GetStringUseCase(
    private val localDefaultsRepository: LocalDefaultsRepository
) {
    operator fun invoke(key: String, onRetrieval: (String) -> Unit){
        onRetrieval(localDefaultsRepository.getString(key = key))
    }
}