package com.mwaibanda.momentum.domain.usecase.cache

import com.mwaibanda.momentum.domain.repository.CacheRepository
import kotlin.coroutines.AbstractCoroutineContextKey

class SetItemUseCase<in T: Any>(
    private val cacheRepository: CacheRepository
) {
    operator fun invoke(key: String, value: T) {
        cacheRepository.setItem(key = key, value = value)
    }
}