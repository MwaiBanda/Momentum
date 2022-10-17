package com.mwaibanda.momentum.domain.repository

interface CacheRepository {
    fun <T: Any> getItem(key: String): T?
    fun <T: Any> setItem(key: String, value: T)
    fun <T: Any> getAllItems(): List<T>
}