package com.mwaibanda.momentum.data

import co.touchlab.stately.ensureNeverFrozen
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*

open class MomentumBase {
    init {
        ensureNeverFrozen()
    }

    companion object {
        private const val PAYMENTS_BASE_URL = "https://momentum-church.glitch.me"
        private const val WEB_HOOK_BASE_URL = "https://hooks.zapier.com/"
        private const val SERMONS_BASE_URL = "https://api.sermoncloud.com/"
        const val WEB_HOOK_URL = "hooks/catch/13196169/bl5p3kh/"
        const val PAYMENT_ENDPOINT = "/checkout"
        const val SERMONS_ENDPOINT = "momentum-church-1/sermons"

        fun HttpRequestBuilder.momentumPayments(path: String) {

            url {
                takeFrom(PAYMENTS_BASE_URL)
                encodedPath = path
            }
        }
        fun HttpRequestBuilder.momentumHooks(path: String) {
            url {
                takeFrom(WEB_HOOK_BASE_URL)
                encodedPath = path
            }
        }
        fun HttpRequestBuilder.momentumSermons(path: String, params: HashMap<String, String>) {
            url {
                takeFrom(SERMONS_BASE_URL)
                encodedPath = path
                params.keys.forEach { key ->
                    parameters.append(name = key, value = params[key] ?: "")
                }
            }
        }
    }
}