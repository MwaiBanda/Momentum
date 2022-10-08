package com.mwaibanda.momentum.android.presentation.transaction

import com.mwaibanda.momentum.data.db.MomentumTransaction
import com.mwaibanda.momentum.domain.controller.TransactionController

class FakeTransactionController: TransactionController {
    private var transactions = mutableListOf<MomentumTransaction>()
    override fun addTransaction(
        description: String,
        date: String,
        amount: Double,
        isSeen: Boolean
    ) {
        transactions.add(
            MomentumTransaction(
                id = (transactions.count() + 1).toLong(),
                description = description,
                date = date,
                amount = amount,
                is_seen = isSeen
            )
        )
    }

    override fun getMomentumTransactions(onCompletion: (List<MomentumTransaction>) -> Unit) {
        onCompletion(transactions)
    }

    override fun deleteAllTransactions() {
        transactions.clear()
    }

    override fun deleteTransactionById(transactionId: Int) {
        val index = transactions.indexOfFirst { it.id == transactionId.toLong() }
        transactions.removeAt(index)
    }
}