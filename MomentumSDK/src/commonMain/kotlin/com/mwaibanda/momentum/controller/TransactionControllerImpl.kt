package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.data.db.Database
import com.mwaibanda.momentum.data.db.DatabaseDriverFactory
import com.mwaibanda.momentum.data.db.MomentumTransaction
import com.mwaibanda.momentum.domain.controller.TransactionController
import org.koin.core.component.KoinComponent

class TransactionControllerImpl(driverFactory: DatabaseDriverFactory): TransactionController {
    private val database = Database(driverFactory)

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

    override fun deleteTransactionById(transactionId: Int) {
        database.deleteTransactionById(transactionId = transactionId)
    }

    override fun getAllTransactions(onCompletion: (List<MomentumTransaction>) -> Unit){
        onCompletion(database.getAllTransactions())
    }

    override fun deleteAllTransactions() {
        database.deleteAllTransactions()
    }
}