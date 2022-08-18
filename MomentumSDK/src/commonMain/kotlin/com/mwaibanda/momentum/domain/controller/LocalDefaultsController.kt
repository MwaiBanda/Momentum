package com.mwaibanda.momentum.domain.controller

interface LocalDefaultsController {
    fun setString(key: String, value: String)
    fun getString(key: String, onRetrieval: (String) -> Unit)
}