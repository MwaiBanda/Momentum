package com.mwaibanda.momentum.android.presentation.transaction

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
    BackHandler {
        onCloseModal()
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
            /*IconButton(onClick = { onCloseModal() }, Modifier.padding(horizontal = 10.dp)) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close transaction icon")
            }*/
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