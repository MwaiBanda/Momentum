package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.data.db.UserTransaction

interface TransactionController {
    fun addTransaction(name: String, amount: Double)
    fun getTransactions(onCompletion: (List<UserTransaction>) -> Unit)
}