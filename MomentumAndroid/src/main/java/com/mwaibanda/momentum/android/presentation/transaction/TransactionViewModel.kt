package com.mwaibanda.momentum.android.presentation.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mwaibanda.momentum.data.db.MomentumTransaction
import com.mwaibanda.momentum.domain.controller.TransactionController
import org.koin.core.component.inject
import org.koin.core.component.KoinComponent
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TransactionViewModel(
    private val transactionController: TransactionController
): ViewModel() {

    private val _transactions: MutableStateFlow<List<MomentumTransaction>> = MutableStateFlow(emptyList())
    val transactions: StateFlow<List<MomentumTransaction>> = _transactions.asStateFlow()

    fun getAllTransactions() {
        transactionController.getAllTransactions { transactions ->
            _transactions.value = transactions
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
    fun deleteTransactionById(transactionId: Int) {
        transactionController.deleteTransactionById(transactionId)
    }
}


