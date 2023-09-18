package com.mwaibanda.momentum.android.core.utils

import java.text.SimpleDateFormat
import java.util.Date

fun getDate(date: String): String {
    var format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss Z")
    val newDate: Date = format.parse(date)

    format = SimpleDateFormat("MMM d, EEE")
    return format.format(newDate)

}