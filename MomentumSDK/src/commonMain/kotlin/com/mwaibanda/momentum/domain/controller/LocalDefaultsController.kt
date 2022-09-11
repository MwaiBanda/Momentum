package com.mwaibanda.momentum.domain.controller

interface LocalDefaultsController {
    fun setString(key: String, value: String)
    fun getString(key: String, onRetrieval: (String) -> Unit)
    fun setInt(key: String, value: Int)
    fun getInt(key: String, onRetrieval: (Int) -> Unit)
    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, onRetrieval: (Boolean) -> Unit)
}