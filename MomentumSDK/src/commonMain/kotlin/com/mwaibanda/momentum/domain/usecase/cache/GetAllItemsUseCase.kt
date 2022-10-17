package com.mwaibanda.momentum.domain.usecase.cache

import com.mwaibanda.momentum.domain.repository.CacheRepository

class GetAllItemsUseCase<out T: Any> (
    private val cacheRepository: CacheRepository
){
    operator fun invoke(): List<T> {
        return cacheRepository.getAllItems()
    }
}