package com.mwaibanda.momentum.domain.usecase.transaction

import com.mwaibanda.momentum.domain.models.Transaction
import com.mwaibanda.momentum.domain.repository.TransactionRepository
import com.mwaibanda.momentum.utils.DataResponse

class GetTransactionsUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(userId: String, onCompletion: (DataResponse<List<Transaction>>) -> Unit){
        onCompletion(transactionRepository.fetchTransactions(userId = userId))
    }
}