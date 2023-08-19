package com.mwaibanda.momentum.utils

import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.hours

fun getMMDDateFormat(): String {
    val dateTime = DateTime()
    val currentMoment = Clock.System.now().minus(3.hours).toString()
    return dateTime.getFormattedDate(currentMoment,"MMM d")

}

fun  getFormattedDate(date: String, format: String): String {
    val dateTime = DateTime()
    return dateTime.getFormattedDate(date, format)
}

