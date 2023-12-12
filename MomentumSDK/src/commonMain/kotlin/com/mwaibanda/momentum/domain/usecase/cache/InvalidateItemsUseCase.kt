package com.mwaibanda.momentum.domain.usecase.cache

import com.mwaibanda.momentum.domain.repository.CacheRepository

class InvalidateItemsUseCase(
    private val cacheRepository: CacheRepository
) {
    operator fun invoke(key: String) {
        cacheRepository.invalidateItems(key = key)
    }
}