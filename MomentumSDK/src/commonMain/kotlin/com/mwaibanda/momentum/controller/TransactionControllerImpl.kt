package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.data.db.Database
import com.mwaibanda.momentum.data.db.DatabaseDriverFactory
import com.mwaibanda.momentum.data.db.MomentumTransaction
import com.mwaibanda.momentum.domain.controller.TransactionController
import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.mwaibanda.momentum.domain.usecase.payment.PostTransactionInfoUseCase
import com.mwaibanda.momentum.utils.Result
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TransactionControllerImpl(driverFactory: DatabaseDriverFactory): TransactionController, KoinComponent {
    private val postTransactionInfoUseCase: PostTransactionInfoUseCase by inject()
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

    override fun postTransactionInfo(
        paymentRequest: PaymentRequest,
        onCompletion: (Result<Int>) -> Unit
    ) {
        scope.launch {
            postTransactionInfoUseCase(paymentRequest) {
                onCompletion(it)
            }
        }
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