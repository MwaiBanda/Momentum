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
        return settings.getString(key = key, defaultValue = "")
    }

    override fun setInt(key: String, value: Int) {
       settings.putInt(key = key, value = value)
    }

    override fun getInt(key: String): Int {
        return settings.getInt(key = key, defaultValue = 0)
    }

    override fun setBoolean(key: String, value: Boolean) {
        settings.putBoolean(key = key, value = value)
    }

    override fun getBoolean(key: String): Boolean {
        return settings.getBoolean(key = key, defaultValue = false)
    }
}