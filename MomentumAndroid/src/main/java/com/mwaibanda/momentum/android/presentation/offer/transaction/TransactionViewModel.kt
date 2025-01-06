package com.mwaibanda.momentum.android.presentation.offer.transaction

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mwaibanda.momentum.data.db.MomentumTransaction
import com.mwaibanda.momentum.domain.controller.TransactionController
import com.mwaibanda.momentum.domain.models.Transaction
import com.mwaibanda.momentum.utils.DataResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TransactionViewModel(
    private val transactionController: TransactionController
): ViewModel() {

    private val _transactions: MutableStateFlow<List<MomentumTransaction>> = MutableStateFlow(emptyList())
    val transactions: StateFlow<List<MomentumTransaction>> = _transactions.asStateFlow()


    private fun getTransactions(userId: String) {
        transactionController.getTransactions(userId = userId) { res ->
            when (res) {
                is DataResponse.Failure -> {
                    Log.d("Transaction/GetFailure", res.message ?: "")
                }
                is DataResponse.Success -> {
                    _transactions.value = res.data?.map { it.toMomentumTransaction() } ?: emptyList()
                    if (_transactions.value.isNotEmpty()) {
                        addTransactions(transactions = _transactions.value)
                    }
                    Log.d("Transaction/GetSuccess", "${_transactions.value}")
                }
            }
        }
    }
    fun getMomentumTransactions(userId: String) {
        transactionController.getMomentumTransactions { transactions ->
            _transactions.value = transactions
            if (transactions.isEmpty()) {
                getTransactions(userId = userId)
            }
        }
    }

    fun postTransactionInfo(transaction: Transaction, onCompletion: () -> Unit) {
        transactionController.postTransactionInfo(transaction = transaction) { res ->
            when (res) {
                is DataResponse.Failure -> {
                    Log.d("Pay/PostFailure", res.message ?: "")
                }
                is DataResponse.Success -> {
                    onCompletion()
                    Log.d("Pay/PostSuccess", res.message ?: "")
                }
            }
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

    private fun addTransactions(transactions: List<MomentumTransaction>) {
        transactionController.addTransactions(transactions = transactions)
    }

    fun deleteAllTransactions() {
        transactionController.deleteAllTransactions()
    }

    fun deleteTransactionById(transactionId: Int) {
        transactionController.deleteTransactionById(transactionId)
    }
}


