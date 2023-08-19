package com.mwaibanda.momentum.utils

expect class DateTime() {
    fun getFormattedDate(
        iso8601Timestamp: String,
        format: String,
    ): String
}