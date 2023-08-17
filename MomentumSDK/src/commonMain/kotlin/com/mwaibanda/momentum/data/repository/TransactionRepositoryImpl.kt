package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.data.MomentumBase
import com.mwaibanda.momentum.data.MomentumBase.Companion.momentumAPI
import com.mwaibanda.momentum.domain.models.PaymentResponse
import com.mwaibanda.momentum.domain.models.Transaction
import com.mwaibanda.momentum.domain.repository.TransactionRepository
import com.mwaibanda.momentum.utils.Result
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.where
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.toList

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