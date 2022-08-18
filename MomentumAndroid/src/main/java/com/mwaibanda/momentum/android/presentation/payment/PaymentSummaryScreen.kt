package com.mwaibanda.momentum.android.presentation.payment

import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mwaibanda.momentum.android.core.utils.Constants
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes
import com.mwaibanda.momentum.android.presentation.auth.AuthViewModel
import com.mwaibanda.momentum.android.presentation.components.BottomSpacing
import com.mwaibanda.momentum.android.presentation.profile.ProfileViewModel
import com.mwaibanda.momentum.android.presentation.transaction.TransactionViewModel
import com.mwaibanda.momentum.domain.models.PaymentRequest
import com.stripe.android.paymentsheet.PaymentSheetContract
import com.stripe.android.paymentsheet.PaymentSheetResult
import org.koin.androidx.compose.getViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun PaymentSummaryScreen(
    navController: NavController,
    contentViewModel: PaymentSummaryContentViewModel  = getViewModel(),
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel,
    transactionViewModel: TransactionViewModel,
    amount: Float,
    canInitiateTransaction: Boolean,
    onTransactionCanProcess: (Boolean) -> Unit,
    onHandlePaymentSheetResult: (paymentResult: PaymentSheetResult, onPaymentSuccess: () -> Unit, onPaymentCancellation: () -> Unit, onPaymentFailure: (String) -> Unit) -> Unit,
    onInitiateCheckout:  (PaymentRequest, ManagedActivityResultLauncher<PaymentSheetContract.Args, PaymentSheetResult>) -> Unit
){
    val stripeLauncher = rememberLauncherForActivityResult(contract = PaymentSheetContract()){ result ->
        onHandlePaymentSheetResult(
            result,
            {
                Log.d("PAY", "Success")
                navController.navigate(NavigationRoutes.PaymentSuccessScreen.route)
                val currentDate = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("MMM d")
                val currentDateString = formatter.format(currentDate)
                transactionViewModel.addTransaction(
                    description = contentViewModel.getTransactionDescription(),
                    date = currentDateString,
                    amount = amount.toDouble(),
                    isSeen = false
                )
                onTransactionCanProcess(true)

            },
            {
                Log.d("PAY", "Cancelled")
                onTransactionCanProcess(true)

            },
            {
                navController.navigate(NavigationRoutes.PaymentFailureScreen.route)
                Log.d("PAY", it)
                onTransactionCanProcess(true)
            }
        )
    }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Column {
            Column {
                Spacer(modifier = Modifier.height(65.dp))
                Text(
                    text = "Payment Summary",
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(10.dp)
                )
                Divider()
                Text(
                    text = "Select multiple options to edit amounts".uppercase(),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    color = Color(Constants.MOMENTUM_ORANGE)
                )
            }
            PaymentSummaryContentScreen(amount = amount.toInt(), contentViewModel = contentViewModel)
        }
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Divider()
            Spacer(modifier = Modifier.height(10.dp))
            Row (
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
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
                enabled = canInitiateTransaction,
                onClick = {
                    profileViewModel.getContactInformation(authViewModel.currentUser?.id ?: "") {
                        onInitiateCheckout(
                            PaymentRequest(
                                fullname = profileViewModel.fullname,
                                email = profileViewModel.email,
                                phone = profileViewModel.phone,
                                description = contentViewModel.getTransactionDescription(),
                                amount = (amount * 100).toInt()
                            ), stripeLauncher
                        )
                    }
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