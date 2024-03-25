package com.mwaibanda.momentum.domain.usecase.cache

import com.mwaibanda.momentum.domain.repository.CacheRepository
import kotlin.native.ObjCName

class InvalidateItemsUseCase(
    private val cacheRepository: CacheRepository
) {
    @ObjCName("execute")
    operator fun invoke(key: String) {
        cacheRepository.invalidateItems(key = key)
    }
}