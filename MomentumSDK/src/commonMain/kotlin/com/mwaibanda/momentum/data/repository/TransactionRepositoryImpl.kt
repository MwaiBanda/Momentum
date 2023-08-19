package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.domain.models.Transaction
import com.mwaibanda.momentum.domain.repository.TransactionRepository
import com.mwaibanda.momentum.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class TransactionRepositoryImpl(
    private val httpClient: HttpClient,
): MomentumBase(), TransactionRepository {
    override suspend fun postTransaction(transaction: Transaction): Result<Int> {

        return try {
            httpClient.post {
                momentumAPI(TRANSACTIONS_ENDPOINT)
                contentType(ContentType.Application.Json)
                setBody(transaction.toRequest())
            }
            Result.Success(200)
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }

    override suspend fun getTransactions(userId: String): Result<List<Transaction>> {
        return try {
            val response: List<Transaction> = httpClient.get {
                momentumAPI("$TRANSACTIONS_ENDPOINT/$userId")
            }.body()
            Result.Success(response)
        } catch (e: Exception)  {
            Result.Failure(e.message.toString())
        }
    }
}