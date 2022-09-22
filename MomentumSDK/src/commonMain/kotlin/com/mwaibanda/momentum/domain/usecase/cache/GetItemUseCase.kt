package com.mwaibanda.momentum.domain.usecase.cache

import com.mwaibanda.momentum.domain.repository.CacheRepository

class GetItemUseCase<out T: Any> (
    private val cacheRepository: CacheRepository
){
    operator fun invoke(key: String): T? {
        return cacheRepository.getItem<T>(key = key)
    }
}