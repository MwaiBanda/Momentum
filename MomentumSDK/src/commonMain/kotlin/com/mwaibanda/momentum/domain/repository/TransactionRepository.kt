package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.Transaction
import com.mwaibanda.momentum.utils.Result

interface TransactionRepository {
    suspend fun postTransaction(transaction: Transaction): Result<Int>
    suspend fun getTransactions(userId: String): Result<List<Transaction>>
}