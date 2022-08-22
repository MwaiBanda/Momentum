package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.mwaibanda.momentum.domain.models.PaymentResponse
import com.mwaibanda.momentum.domain.repository.PaymentRepository
import com.mwaibanda.momentum.utils.Result
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

internal class PaymentRepositoryImpl(
    private val httpClient: HttpClient
): PaymentRepository, MomentumBase() {

    override suspend fun prepareCheckout(paymentRequest: PaymentRequest): Result<PaymentResponse> {
        return try {
            val response: PaymentResponse = httpClient.post {
                momentumAPI(PAYMENT_ENDPOINT)
                contentType(ContentType.Application.Json)
                setBody(paymentRequest)
            }.body()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }

    }

    override suspend fun postTransactionInfo(paymentRequest: PaymentRequest): Result<Int> {
        return try {
            val response = httpClient.post {
                momentumHooks(WEB_HOOK_URL)
                contentType(ContentType.Application.Json)
                setBody(paymentRequest)
            }.status
            Result.Success(response.value)
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }
}