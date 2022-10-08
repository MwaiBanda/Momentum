package com.mwaibanda.momentum.data.repository

import com.mwaibanda.momentum.domain.models.Transaction
import com.mwaibanda.momentum.domain.repository.TransactionRepository
import com.mwaibanda.momentum.utils.Result
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.where
import kotlinx.coroutines.flow.toList

class TransactionRepositoryImpl(
    private val db: FirebaseFirestore
): TransactionRepository {
    override suspend fun postTransaction(transaction: Transaction): Result<Int> {

        return try {
            db.collection("Transactions")
                .document
                .set(Transaction.serializer(), transaction, encodeDefaults = true)
            Result.Success(200)
        } catch (e: Exception) {
            Result.Failure(e.message.toString())
        }
    }

    override suspend fun getTransactions(userId: String): Result<List<Transaction>> {
        return try {
            val res: List<Transaction> = db.collection("Transactions")
                .where("userId", userId)
                .get().documents.map { it.data() }

            Result.Success(res)
        } catch (e: Exception)  {
            Result.Failure(e.message.toString())
        }
    }
}