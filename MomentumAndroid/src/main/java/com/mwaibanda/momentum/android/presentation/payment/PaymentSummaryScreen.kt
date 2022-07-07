package com.mwaibanda.momentum.android.presentation.payment

import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.stripe.android.paymentsheet.PaymentSheetContract
import com.stripe.android.paymentsheet.PaymentSheetResult
import java.time.temporal.TemporalAmount

@Composable
fun PaymentSummaryScreen(
    amount: Float,
    onHandlePaymentSheetResult: (paymentResult: PaymentSheetResult, onPaymentSuccess: () -> Unit, onPaymentCancellation: () -> Unit, onPaymentFailure: (String) -> Unit) -> Unit,
    onInitiateCheckout: (PaymentRequest, ManagedActivityResultLauncher<PaymentSheetContract.Args, PaymentSheetResult>) -> Unit
){
    val stripeLauncher = rememberLauncherForActivityResult(contract = PaymentSheetContract()){ result ->
        onHandlePaymentSheetResult(
            result,
            {
                Log.d("PAY", "Success")
            },
            {
                Log.d("PAY", "Cancelled")
            },
            {
                Log.d("PAY", it)
            }
        )
    }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                      onInitiateCheckout(PaymentRequest((amount  * 100).toInt()), stripeLauncher)
            },
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(55.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE55F1F))
        ) {
            Text(
                text = "Confirm",
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}