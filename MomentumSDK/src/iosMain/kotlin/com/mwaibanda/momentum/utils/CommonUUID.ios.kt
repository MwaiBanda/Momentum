package com.mwaibanda.momentum.utils

import platform.Foundation.NSUUID

actual fun randomUUID(): String {
    return NSUUID.UUID().UUIDString
}
