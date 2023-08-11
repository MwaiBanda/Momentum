package com.mwaibanda.momentum.data

import co.touchlab.stately.ensureNeverFrozen
import io.ktor.client.request.*
import io.ktor.http.*

open class MomentumBase {
    init {
        ensureNeverFrozen()
    }

    companion object {
        private const val BASE_URL = "https://services.momentumchurch.dev"
        private const val API_ROUTE = "/api/v1"
        const val PAYMENT_ENDPOINT = "/payments"
        const val SERMONS_ENDPOINT = "/sermons"

        private const val WEB_HOOK_BASE_URL = "https://hooks.zapier.com/"
        const val WEB_HOOK_URL = "hooks/catch/13196169/bl5p3kh/"

        fun HttpRequestBuilder.momentumAPI(path: String, params: HashMap<String, String> = hashMapOf()) {
            url {
                takeFrom(BASE_URL)
                encodedPath = "$API_ROUTE${path}"
                if (params.isNotEmpty()) {
                    params.keys.forEach { key ->
                        parameters.append(name = key, value = params[key] ?: "")
                    }
                }
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