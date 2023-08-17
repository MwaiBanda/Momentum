package com.mwaibanda.momentum.domain.models

import com.mwaibanda.momentum.data.db.MomentumTransaction
import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val id: String,
    val description: String,
    val amount: Int,
    val date: String,
    val createdOn: String,
    val user: User
) {
    fun toMomentumTransaction(): MomentumTransaction {
        return MomentumTransaction(
            id = (1000L..9999L).random(),
            description = description,
            date = date,
            amount = amount.toDouble(),
            is_seen = false
        )
    }

    fun toRequest(): TransactionRequest {
        return TransactionRequest(
            id = id,
            description = description,
            amount = amount,
            date = date,
            userId = user.userId
        )
    }
}