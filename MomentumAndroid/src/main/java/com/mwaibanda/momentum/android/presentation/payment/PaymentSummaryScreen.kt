package com.mwaibanda.momentum.android.presentation.payment

import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes
import com.mwaibanda.momentum.android.presentation.auth.AuthViewModel
import com.mwaibanda.momentum.android.presentation.components.BottomSpacing
import com.mwaibanda.momentum.android.presentation.profile.ProfileViewModel
import com.mwaibanda.momentum.android.presentation.transaction.TransactionViewModel
import com.mwaibanda.momentum.domain.models.Payment
import com.mwaibanda.momentum.domain.models.Transaction
import com.mwaibanda.momentum.domain.models.User
import com.stripe.android.paymentsheet.PaymentSheetContract
import com.stripe.android.paymentsheet.PaymentSheetResult
import org.koin.androidx.compose.getViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun PaymentSummaryScreen(
    navController: NavController,
    contentViewModel: PaymentSummaryContentViewModel = getViewModel(),
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel,
    transactionViewModel: TransactionViewModel,
    amount: Float,
    canInitiateTransaction: Boolean,
    onShowModal: (Boolean) -> Unit,
    onTransactionCanProcess: (Boolean) -> Unit,
    onHandlePaymentSheetResult: (paymentResult: PaymentSheetResult, onPaymentSuccess: () -> Unit, onPaymentCancellation: () -> Unit, onPaymentFailure: (String) -> Unit) -> Unit,
    onInitiateCheckout:  (Payment, ManagedActivityResultLauncher<PaymentSheetContract.Args, PaymentSheetResult>) -> Unit
){
    val currentDate = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("MMM d")
    val currentDateString = formatter.format(currentDate)
    var showOtherTopUp by remember {
        mutableStateOf(false)
    }
    val otherLabel = rememberSaveable { mutableStateOf("") }

    val stripeLauncher = rememberLauncherForActivityResult(contract = PaymentSheetContract()){ result ->
        onHandlePaymentSheetResult(
            result,
            {
                Log.d("PAY", "Success")
                navController.navigate(NavigationRoutes.PaymentSuccessScreen.route)

                transactionViewModel.postTransactionInfo(
                    transaction = Transaction(
                        id = "",
                        date = currentDateString,
                        description = contentViewModel.getTransactionDescription(otherLabel.value),
                        amount = amount.toInt(),
                        createdOn = "",
                        user = User(
                            fullname = profileViewModel.fullname,
                            email = profileViewModel.email,
                            phone = profileViewModel.phone,
                            userId = authViewModel.currentUser?.id ?: ""
                        )
                    )
                ) {
                    transactionViewModel.addTransaction(
                        description = contentViewModel.getTransactionDescription(otherLabel.value),
                        date = currentDateString,
                        amount = amount.toDouble(),
                        isSeen = false
                    )
                }
                onTransactionCanProcess(true)

            },
            {
                Log.d("PAY", "Cancelled")
                Log.d("PAY", contentViewModel.getTransactionDescription(otherLabel.value))
                onTransactionCanProcess(true)

            },
            {
                navController.navigate(NavigationRoutes.PaymentFailureScreen.route)
                Log.d("PAY", it)
                onTransactionCanProcess(true)
            }
        )
    }

    LaunchedEffect(key1 = contentViewModel.otherIsSelected, block = {
        if (contentViewModel.otherIsSelected) {
            showOtherTopUp = true
            onShowModal(true)
        }
    })

    Box {
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
                        color = Color(C.MOMENTUM_ORANGE)
                    )
                }
                PaymentSummaryContentScreen(
                    otherLabel = otherLabel.value,
                    amount = amount.toInt(),
                    contentViewModel = contentViewModel
                )
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
                    enabled = canInitiateTransaction && contentViewModel.selectedLabels.isNotEmpty(),
                    onClick = {
                        profileViewModel.getContactInformation(authViewModel.currentUser?.id ?: "") {
                            onInitiateCheckout(
                                Payment(
                                    amount = (amount * 100).toInt(),
                                    fullname = profileViewModel.fullname,
                                    email = profileViewModel.email,
                                    phone = profileViewModel.phone,
                                )
                                ,stripeLauncher
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

        AnimatedVisibility(
            visible = showOtherTopUp,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier
                        .clip(RoundedCornerShape(5))
                        .background(Color.White)
                        .padding(20.dp)
                        .fillMaxWidth(0.7f)
                ) {
                    Column {
                        Text(text = "What Ministry Are You Giving To?", fontWeight = FontWeight.Bold)
                        BasicTextField(
                            value = otherLabel.value,
                            onValueChange = { otherLabel.value = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .padding(vertical = 10.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 5.dp)
                        )
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Button(
                                modifier = Modifier
                                    .weight(0.4f)
                                    .padding(end = 5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(C.MOMENTUM_ORANGE),
                                    contentColor = Color.White
                                ),
                                onClick = {
                                    showOtherTopUp = false
                                    contentViewModel.selectedLabels.removeIf {
                                        it == PaymentSummaryContentViewModel.ToggleLabel.OTHER
                                    }
                                    contentViewModel.otherIsSelected  = false
                                    contentViewModel.otherAmount = "0"
                                    onShowModal(false)
                            }) {
                                Text(text = "Cancel")
                            }
                            Button(
                                modifier = Modifier
                                    .weight(0.4f)
                                    .padding(start = 5.dp),
                                onClick = {
                                    showOtherTopUp = false
                                    onShowModal(false)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(C.MOMENTUM_ORANGE),
                                    contentColor = Color.White
                                ),
                            ) {
                                Text(text = "Save")
                            }
                        }
                    }

                }
            }
        }
    }
}