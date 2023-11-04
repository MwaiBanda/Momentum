package com.mwaibanda.momentum.android.presentation.auth

import com.mwaibanda.momentum.domain.controller.LocalDefaultsController

class FakeLocalDefaultsController: LocalDefaultsController {
    private val storage = hashMapOf<String, Any>()
    override fun setString(key: String, value: String) {
        storage[key] = value
    }

    override fun getString(key: String, onRetrieval: (String) -> Unit) {
        onRetrieval(storage[key] as? String ?: "")
    }

    override fun setInt(key: String, value: Int) {
        storage[key] = value
    }

    override fun getInt(key: String, onRetrieval: (Int) -> Unit) {
        onRetrieval(storage[key] as? Int ?: 0)
    }

    override fun setBoolean(key: String, value: Boolean) {
        storage[key] = value
    }

    override fun getBoolean(key: String, onRetrieval: (Boolean) -> Unit) {
        onRetrieval(storage[key] as? Boolean ?: false)
    }
}