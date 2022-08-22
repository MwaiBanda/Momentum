package com.mwaibanda.momentum.android.presentation.transaction

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mwaibanda.momentum.data.db.MomentumTransaction
import com.mwaibanda.momentum.domain.controller.TransactionController
import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.mwaibanda.momentum.utils.Result
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

    fun postTransactionInfo(paymentRequest: PaymentRequest, onCompletion: () -> Unit) {
        transactionController.postTransactionInfo(paymentRequest = paymentRequest) { res ->
            when (res) {
                is Result.Failure -> {
                    Log.d("Pay/PostFailure", res.message ?: "")
                }
                is Result.Success -> {
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

    fun deleteAllTransactions() {
        transactionController.deleteAllTransactions()
    }
    fun deleteTransactionById(transactionId: Int) {
        transactionController.deleteTransactionById(transactionId)
    }
}


