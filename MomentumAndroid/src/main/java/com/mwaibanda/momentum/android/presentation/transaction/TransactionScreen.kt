package com.mwaibanda.momentum.android.presentation.transaction

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.mwaibanda.momentum.android.presentation.auth.AuthViewModel
import com.mwaibanda.momentum.android.presentation.components.TransactionLabel
import com.mwaibanda.momentum.android.presentation.meals.Modal

@Composable
fun TransactionScreen(
    authViewModel: AuthViewModel,
    transactionViewModel: TransactionViewModel,
    onCloseModal: () -> Unit
) {
    val transactions by transactionViewModel.transactions.collectAsState()

    LaunchedEffect(key1 = Unit) {
        if (authViewModel.currentUser?.isGuest?.not() == true) {
            transactionViewModel.getMomentumTransactions(userId = authViewModel.currentUser?.id ?: "")
        }
    }

    Modal(onCloseModal, "Transactions") {
        Divider()
        transactions.forEach { transaction ->
            TransactionLabel(
                description = transaction.description,
                amount = transaction.amount,
                date = transaction.date
            )
            Divider()
        }
    }
}