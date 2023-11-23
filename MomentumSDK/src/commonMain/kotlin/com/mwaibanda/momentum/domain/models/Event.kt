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
    private fun getFormattedStartTime(): String {
        return getFormattedDate(startTime, "h:mma").replace(":00", "")
    }
    private fun getFormattedEndTime(): String {
        return getFormattedDate(endTime, "h:mma").replace(":00", "")
    }
    fun getDisplayEventTime(): String {
        val startTime = getFormattedStartTime()
        val endTime = getFormattedEndTime()
        var result = "$startTime-$endTime"
        if (startTime.contains("AM") && endTime.contains("AM") || startTime.contains("PM") && endTime.contains("PM")) {
            result ="${startTime.replace("AM", "").replace("PM", "")}-$endTime"
        }
        return  result.lowercase()
    }
}
