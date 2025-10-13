package com.mwaibanda.momentum.utils

import kotlin.time.Clock.System
import kotlin.time.Duration.Companion.hours
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun getMMDDateFormat(): String {
    val dateTime = DateTime()
    val currentMoment = System.now().minus(3.hours).toString()
    return dateTime.getFormattedDate(currentMoment,"MMM d")

}

fun  getFormattedDate(date: String, format: String): String {
    val dateTime = DateTime()
    return dateTime.getFormattedDate(date, format)
}

