package com.mwaibanda.momentum.android.presentation.payment

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.mwaibanda.momentum.android.R
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes
import com.mwaibanda.momentum.android.presentation.components.BottomSpacing

@Composable
fun PaymentSuccessScreen(navController: NavController) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.success)
    )
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 1,
        isPlaying = true,
        speed = 1f,
        restartOnPlay = false

    )
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier.size(250.dp)
            )
            Text(
                text = "Payment Success",
                fontWeight = FontWeight.ExtraBold,
                color = Color(C.MOMENTUM_ORANGE),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = """
                Your payment has been made successfully.
                For more details. Check the transactions
                tab to see or give again, in the Accounts
                section.
                """.trimIndent(),
                textAlign = TextAlign.Center
            )
        }
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    navController.navigate(NavigationRoutes.OfferScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(55.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(C.MOMENTUM_ORANGE))
            ) {
                Text(
                    text = "Back",
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