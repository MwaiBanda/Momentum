package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.domain.models.Payment
import com.mwaibanda.momentum.domain.models.PaymentResponse
import com.mwaibanda.momentum.domain.repository.PaymentRepository
import com.mwaibanda.momentum.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post

internal class PaymentRepositoryImpl(
    private val httpClient: HttpClient
): PaymentRepository, MomentumBase() {

    override suspend fun prepareCheckout(transaction: Payment): Result<PaymentResponse> {
        return try {
            val response: PaymentResponse = httpClient.post {
                momentumAPI(PAYMENT_ENDPOINT, transaction)
            }.body()
            Result.Success(response)
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }

    }
}