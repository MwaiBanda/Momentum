package com.mwaibanda.momentum.android.presentation.transaction

import com.mwaibanda.momentum.data.db.MomentumTransaction
import com.mwaibanda.momentum.domain.controller.TransactionController
import com.mwaibanda.momentum.domain.models.Transaction
import com.mwaibanda.momentum.utils.DataResponse

class FakeTransactionController: TransactionController {
    private var transactions = mutableListOf<MomentumTransaction>()
    override fun addTransactions(transactions: List<MomentumTransaction>) {
        this.transactions.addAll(transactions)
    }

    override fun postTransactionInfo(
        transaction: Transaction,
        onCompletion: (DataResponse<Int>) -> Unit,
    ) {
        transactions.add(transaction.toMomentumTransaction())
    }

    override fun getTransactions(
        userId: String,
        onCompletion: (DataResponse<List<Transaction>>) -> Unit,
    ) {
        onCompletion(DataResponse.Success(emptyList()))
    }

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