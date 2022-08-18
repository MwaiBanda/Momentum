package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.domain.repository.LocalDefaultsRepository
import com.russhwolf.settings.Settings

class LocalDefaultsRepositoryImpl(
    private val  settings: Settings
): LocalDefaultsRepository {
    override fun setString(key: String, value: String) {
        settings.putString(key = key, value = value)
    }

    override fun getString(key: String): String {
        return settings.getString(key = key)
    }
}