package com.mwaibanda.momentum.android.presentation.payment

import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes
import com.mwaibanda.momentum.android.presentation.components.BottomSpacing
import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.stripe.android.paymentsheet.PaymentSheetContract
import com.stripe.android.paymentsheet.PaymentSheetResult
import java.text.DecimalFormat
import java.time.temporal.TemporalAmount

@Composable
fun PaymentSummaryScreen(
    navController: NavController,
    amount: Float,
    onHandlePaymentSheetResult: (paymentResult: PaymentSheetResult, onPaymentSuccess: () -> Unit, onPaymentCancellation: () -> Unit, onPaymentFailure: (String) -> Unit) -> Unit,
    onInitiateCheckout: (PaymentRequest, ManagedActivityResultLauncher<PaymentSheetContract.Args, PaymentSheetResult>) -> Unit
){
    val stripeLauncher = rememberLauncherForActivityResult(contract = PaymentSheetContract()){ result ->
        onHandlePaymentSheetResult(
            result,
            {
                navController.navigate(NavigationRoutes.PaymentSuccessScreen.route)
                Log.d("PAY", "Success")
            },
            {
                Log.d("PAY", "Cancelled")
            },
            {
                navController.navigate(NavigationRoutes.PaymentFailureScreen.route)
                Log.d("PAY", it)
            }
        )
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Column {
            Column {
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = "Payment Summary",
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(10.dp)
                )
                Divider()
            }
            PaymentSummaryContentScreen()
        }
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Divider()
            Spacer(modifier = Modifier.height(10.dp))
            Row (
                Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total",
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.h6,
                )
                Text(
                    text = "$" + String.format("%.2f", amount),
                    Modifier.padding(end = 10.dp),
                    fontWeight = FontWeight.Light,
                    style = MaterialTheme.typography.h6,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Divider()
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    onInitiateCheckout(PaymentRequest((amount * 100).toInt()), stripeLauncher)
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
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
            Spacer(modifier = Modifier.height(10.dp))
            Divider()
            BottomSpacing()
        }
    }
}