package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.data.db.Database
import com.mwaibanda.momentum.data.db.DatabaseDriverFactory
import com.mwaibanda.momentum.data.db.UserTransaction
import com.mwaibanda.momentum.domain.controller.TransactionController

class TransactionControllerImpl(driverFactory: DatabaseDriverFactory): TransactionController {
    private val database = Database(driverFactory)

    override fun addTransaction(name: String, amount: Double) {
        database.insertTransaction(name, amount)
    }

    override fun getTransactions(onCompletion: (List<UserTransaction>) -> Unit){
        onCompletion(database.getAllTransactions())
    }
}