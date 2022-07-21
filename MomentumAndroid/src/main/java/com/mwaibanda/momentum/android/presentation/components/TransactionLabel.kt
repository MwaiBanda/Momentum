package com.mwaibanda.momentum.android.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.R
import java.time.temporal.TemporalAmount

@Composable
fun TransactionLabel(description: String, amount: Double, date: String ) {
    Row(Modifier.fillMaxWidth().padding(10.dp).padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.momentum_circle),
                contentDescription = "logo",
                modifier = Modifier.size(45.dp)
            )
            Column(Modifier.padding(start = 10.dp)) {
                Text(
                    text = "Momentum",
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.subtitle1,
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.body2,
                    color = Color.Gray
                )
            }
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "$" + String.format("%.2f", amount),
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.subtitle1,
            )
            Text(
                text = date,
                style = MaterialTheme.typography.body2,
                color = Color.Gray
            )
        }
    }
}

@Composable
@Preview
fun TransactionLabelPreview() {
    TransactionLabel(
        description = "Paid $10: Offering, $30: Tithe",
        amount = 30.99,
        date = "July 19"
    )
}