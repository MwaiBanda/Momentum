package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.data.db.Database
import com.mwaibanda.momentum.data.db.DatabaseDriverFactory
import com.mwaibanda.momentum.data.db.MomentumTransaction
import com.mwaibanda.momentum.domain.controller.TransactionController
import com.mwaibanda.momentum.domain.models.Transaction
import com.mwaibanda.momentum.domain.usecase.transaction.GetTransactionsUseCase
import com.mwaibanda.momentum.domain.usecase.transaction.PostTransactionUseCase
import com.mwaibanda.momentum.utils.Result
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TransactionControllerImpl(driverFactory: DatabaseDriverFactory): TransactionController, KoinComponent {
    private val postTransactionUseCase: PostTransactionUseCase by inject()
    private val getTransactionsUseCase: GetTransactionsUseCase by inject()
    private val database = Database(driverFactory)
    private val scope = MainScope()

    override fun addTransaction(
        description: String,
        date: String,
        amount: Double,
        isSeen: Boolean,
    ) {
        database.insertTransaction(
            description = description,
            date = date,
            amount = amount,
            isSeen = isSeen
        )
    }

    override fun addTransactions(transactions: List<MomentumTransaction>) {
        database.insertTransactions(transactions = transactions)
    }

    override fun postTransactionInfo(
        transaction: Transaction,
        onCompletion: (Result<Int>) -> Unit
    ) {
        scope.launch {
            postTransactionUseCase(transaction) {
                onCompletion(it)
            }
        }
    }

    override fun deleteTransactionById(transactionId: Int) {
        database.deleteTransactionById(transactionId = transactionId)
    }

    override fun getTransactions(
        userId: String,
        onCompletion: (Result<List<Transaction>>) -> Unit
    ) {
        scope.launch {
            getTransactionsUseCase(userId = userId) {
                onCompletion(it)
            }
        }
    }

    override fun getMomentumTransactions(onCompletion: (List<MomentumTransaction>) -> Unit){
        onCompletion(database.getAllTransactions())
    }

    override fun deleteAllTransactions() {
        database.deleteAllTransactions()
    }
}