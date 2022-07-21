package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.mwaibanda.momentum.domain.models.PaymentResponse
import com.mwaibanda.momentum.domain.repository.PaymentRepository
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

internal class PaymentRepositoryImpl(
    private val httpClient: HttpClient
): PaymentRepository, MomentumBase() {

    override suspend fun prepareCheckout(paymentRequest: PaymentRequest): PaymentResponse {
        val response: PaymentResponse = httpClient.post {
            momentumAPI(PAYMENT_ENDPOINT)
            contentType(ContentType.Application.Json)
            body = paymentRequest
        }
        return response
    }
}