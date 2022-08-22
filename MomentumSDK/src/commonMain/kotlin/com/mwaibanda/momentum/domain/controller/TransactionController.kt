package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.data.db.MomentumTransaction
import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.mwaibanda.momentum.utils.Result


interface TransactionController {
    fun addTransaction(
        description: String,
        date: String,
        amount: Double,
        isSeen: Boolean,
    )
    fun postTransactionInfo(paymentRequest: PaymentRequest, onCompletion: (Result<Int>) -> Unit)
    fun getAllTransactions(onCompletion: (List<MomentumTransaction>) -> Unit)
    fun deleteAllTransactions()
    fun deleteTransactionById(transactionId: Int)
}