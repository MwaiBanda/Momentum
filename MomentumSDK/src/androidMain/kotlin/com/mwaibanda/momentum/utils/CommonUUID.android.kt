package com.mwaibanda.momentum.utils

import java.util.UUID

actual fun randomUUID(): String {
    return UUID.randomUUID().toString().uppercase()
}