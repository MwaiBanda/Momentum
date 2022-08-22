package com.mwaibanda.momentum.data

import co.touchlab.stately.ensureNeverFrozen
import io.ktor.client.request.*
import io.ktor.http.*

open class MomentumBase {
    init {
        ensureNeverFrozen()
    }

    companion object {
        private const val BASE_URL = "https://momentum-church.glitch.me"
        private const val WEB_HOOK_BASE_URL = "https://hooks.zapier.com/"
        const val WEB_HOOK_URL = "hooks/catch/13196169/bl5p3kh/"
        const val PAYMENT_ENDPOINT = "/checkout"

        fun HttpRequestBuilder.momentumAPI(path: String) {
            url {
                takeFrom(BASE_URL)
                encodedPath = path
            }
        }
        fun HttpRequestBuilder.momentumHooks(path: String) {
            url {
                takeFrom(WEB_HOOK_BASE_URL)
                encodedPath = path
            }
        }
    }
}