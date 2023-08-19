package com.mwaibanda.momentum.utils
import platform.Foundation.*
actual class DateTime actual constructor() {
    actual fun getFormattedDate(
        iso8601Timestamp: String,
        format: String,
    ): String {
        val date = getDateFromIso8601Timestamp(iso8601Timestamp) ?: return ""

        val dateFormatter = NSDateFormatter()
        dateFormatter.timeZone = NSTimeZone.localTimeZone
        dateFormatter.locale = NSLocale.autoupdatingCurrentLocale
        dateFormatter.dateFormat = format
        return dateFormatter.stringFromDate(date)
    }

    private fun getDateFromIso8601Timestamp(string: String): NSDate? {
        return NSISO8601DateFormatter().dateFromString(string)
    }
}