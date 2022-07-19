package com.mwaibanda.momentum.android.presentation.transaction

import androidx.lifecycle.ViewModel
import com.mwaibanda.momentum.domain.controller.TransactionController
import org.koin.core.component.inject
import org.koin.core.component.KoinComponent

class TransactionViewModel: ViewModel(), KoinComponent {
    private val transactionController: TransactionController by inject()

    fun getAllTransactions() {
        transactionController.getAllTransactions {

        }
    }

    fun addTransaction(
        description: String,
        date: String,
        amount: Double,
        isSeen: Boolean,
    ) {
        transactionController.addTransaction(description, date, amount, isSeen)
    }

    fun deleteAllTransactions() {
        transactionController.deleteAllTransactions()
    }

}