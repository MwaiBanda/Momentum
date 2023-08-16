package com.mwaibanda.momentum.domain.models

import com.mwaibanda.momentum.data.db.MomentumTransaction
import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val fullname: String,
    val email: String,
    val phone: String,
    val description: String,
    val amount: Int,
    val date: String = "",
    val userId: String = ""
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
}