package com.mwaibanda.momentum.data

import co.touchlab.stately.ensureNeverFrozen
import io.ktor.client.request.*
import io.ktor.http.*

open class MomentumBase {
    init {
        ensureNeverFrozen()
    }

    companion object {
        private const val BASE_URL = "https://momentumchurch.glitch.me"
        const val PAYMENT_ENDPOINT = "/checkout"

        fun HttpRequestBuilder.momentumAPI(path: String) {
            url {
                takeFrom(BASE_URL)
                encodedPath = path
            }
        }
    }
}