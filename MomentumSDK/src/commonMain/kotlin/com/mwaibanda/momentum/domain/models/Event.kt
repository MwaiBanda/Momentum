package com.mwaibanda.momentum.domain.models

import com.mwaibanda.momentum.utils.getFormattedDate

data class Event(
    val id: String,
    val startTime: String,
    val endTime: String,
    val description: String,
    val intervalSummary: String,
    val name: String,
    val thumbnail: String
) {
    fun getFormattedStartDate(): String {
        return getFormattedDate(startTime, "EEEE, MMM YY")
    }
    fun getFormattedStartTime(): String {
        return getFormattedDate(startTime, "hh:mm a")
    }
    fun getFormattedEndTime(): String {
        return getFormattedDate(endTime, "hh:mm a")
    }
}
