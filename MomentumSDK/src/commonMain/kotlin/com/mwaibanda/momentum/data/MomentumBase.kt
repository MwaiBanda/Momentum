package com.mwaibanda.momentum.data

import co.touchlab.stately.ensureNeverFrozen
import com.mwaibanda.momentum.utils.MultiplatformConstants
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom

open class MomentumBase {
    init {
        ensureNeverFrozen()
    }

    companion object {
        private const val BASE_URL = "https://services.momentumchurch.dev"
        private const val API_ROUTE = "/api/v1"
        const val PAYMENT_ENDPOINT = "/payments"
        const val NOTIFICATIONS_ENDPOINT = "/notifications"
        const val TRANSACTIONS_ENDPOINT = "/transactions"
        const val SERMONS_ENDPOINT = "/sermons"
        const val MESSAGE_ENDPOINT = "/messages"
        const val USERS_ENDPOINT = "/users"
        const val MEALS_ENDPOINT = "/meals"
        const val VOLUNTEERED_MEAL_ENDPOINT="$MEALS_ENDPOINT/meal"

        fun HttpRequestBuilder.momentumAPI(path: String, params: HashMap<String, String> = hashMapOf()) {
            url {
                takeFrom(BASE_URL)
                encodedPath = "$API_ROUTE${path}"
                headers.append("Authorization", "Bearer ${MultiplatformConstants.API_KEY}")
                if (params.isNotEmpty()) {
                    params.keys.forEach { key ->
                        parameters.append(name = key, value = params[key] ?: "")
                    }
                }
            }
        }
    }
}