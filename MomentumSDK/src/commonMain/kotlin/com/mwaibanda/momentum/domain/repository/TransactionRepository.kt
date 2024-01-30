package com.mwaibanda.momentum.domain.repository

import com.mwaibanda.momentum.domain.models.Transaction
import com.mwaibanda.momentum.utils.DataResponse

interface TransactionRepository {
    suspend fun postTransaction(transaction: Transaction): DataResponse<Int>
    suspend fun fetchTransactions(userId: String): DataResponse<List<Transaction>>
}