package com.mwaibanda.momentum.domain.usecase.localDefaults

import com.mwaibanda.momentum.domain.repository.LocalDefaultsRepository

class GetIntUseCase (
    private val localDefaultsRepository: LocalDefaultsRepository
) {
    operator fun invoke(key: String, onRetrieval: (Int) -> Unit){
        onRetrieval(localDefaultsRepository.getInt(key))
    }
}