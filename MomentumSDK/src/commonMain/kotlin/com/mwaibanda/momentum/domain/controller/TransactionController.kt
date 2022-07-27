package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.data.db.MomentumTransaction


interface TransactionController {
    fun addTransaction(
        description: String,
        date: String,
        amount: Double,
        isSeen: Boolean,
    )
    fun getAllTransactions(onCompletion: (List<MomentumTransaction>) -> Unit)
    fun deleteAllTransactions()
    fun deleteTransactionById(transactionId: Int)
}