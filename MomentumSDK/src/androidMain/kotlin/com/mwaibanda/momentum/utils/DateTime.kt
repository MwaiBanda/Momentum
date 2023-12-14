package com.mwaibanda.momentum.utils

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter




actual class DateTime actual constructor() {
    actual fun getFormattedDate(
        iso8601Timestamp: String,
        format: String,
    ): String {

        val date = getDateFromIso8601Timestamp(iso8601Timestamp)
        val formatter = DateTimeFormatter.ofPattern(format).withZone(ZoneId.of("America/Chicago"))

        return date.format(formatter)
    }

    private fun getDateFromIso8601Timestamp(string: String): ZonedDateTime {
        return ZonedDateTime.parse(string)
    }
}