package com.mwaibanda.momentum.domain.usecase.transaction

import com.mwaibanda.momentum.domain.models.Transaction
import com.mwaibanda.momentum.domain.repository.TransactionRepository
import com.mwaibanda.momentum.utils.DataResponse

class PostTransactionUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(request: Transaction, onCompletion: (DataResponse<Int>) -> Unit){
        onCompletion(transactionRepository.postTransaction(request))
    }
}