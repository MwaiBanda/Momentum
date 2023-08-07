package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.domain.models.Transaction
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

    override suspend fun prepareCheckout(transaction: Transaction): Result<PaymentResponse> {
        return try {
            val response: PaymentResponse = httpClient.post {
                momentumAPI(PAYMENT_ENDPOINT)
                contentType(ContentType.Application.Json)
                setBody(transaction)
            }.body()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }

    }
}