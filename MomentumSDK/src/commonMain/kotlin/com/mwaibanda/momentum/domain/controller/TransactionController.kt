package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.data.db.MomentumTransaction
import com.mwaibanda.momentum.domain.models.Transaction
import com.mwaibanda.momentum.utils.Result


interface TransactionController {
    fun addTransaction(
        description: String,
        date: String,
        amount: Double,
        isSeen: Boolean,
    )
    fun addTransactions(transactions: List<MomentumTransaction>)
    fun postTransactionInfo(transaction: Transaction, onCompletion: (Result<Int>) -> Unit)
    fun getTransactions(userId: String, onCompletion: (Result<List<Transaction>>) -> Unit)
    fun getMomentumTransactions(onCompletion: (List<MomentumTransaction>) -> Unit)
    fun deleteAllTransactions()
    fun deleteTransactionById(transactionId: Int)
}