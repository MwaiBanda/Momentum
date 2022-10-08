package com.mwaibanda.momentum.android.presentation.transaction

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.presentation.auth.AuthViewModel
import com.mwaibanda.momentum.android.presentation.components.TransactionLabel

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

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.95f)) {
        Row(Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Transactions",
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(10.dp)
                    .padding(bottom = 8.dp)
            )
            IconButton(onClick = { onCloseModal() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close transaction icon")
            }
        }
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