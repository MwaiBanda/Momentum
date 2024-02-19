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
        return if (startTime == "placeholder") {
            "Thursday, Nov 2023"
        } else {
            getFormattedDate(startTime, "EEEE, MMM d")
        }
    }

    private fun getFormattedStartTime(): String {
        return if (startTime == "placeholder") {
            "00:00pm"
        } else {
            getFormattedDate(startTime, "h:mma").replace(":00", "")
        }
    }

    private fun getFormattedEndTime(): String {
        return if (endTime == "placeholder") {
            "00:00pm"
        } else {
            getFormattedDate(endTime, "h:mma").replace(":00", "")
        }
    }

    fun getDisplayEventTime(): String {
        val startTime = getFormattedStartTime()
        val endTime = getFormattedEndTime()
        var result = "$startTime-$endTime"
        if (startTime.contains("AM") && endTime.contains("AM") || startTime.contains("PM") && endTime.contains(
                "PM"
            )
        ) {
            result = "${startTime.replace("AM", "").replace("PM", "")}-$endTime"
        }
        return result.lowercase()
    }

    fun containsTerm(term: String): Boolean {
        return name.lowercase().contains(term.lowercase()) ||
               getFormattedStartDate().lowercase().contains(term.lowercase()) ||
               term.isEmpty()
    }
}
