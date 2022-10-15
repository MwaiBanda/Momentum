package com.mwaibanda.momentum.android.presentation.offer

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mwaibanda.momentum.android.core.utils.AppReviewRequester
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes
import com.mwaibanda.momentum.android.presentation.auth.AuthViewModel
import com.mwaibanda.momentum.android.presentation.components.BlurredBackground
import com.mwaibanda.momentum.android.presentation.components.BottomSpacing
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@Composable
fun OfferScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    offerViewModel: OfferViewModel = getViewModel(),
    appReviewRequester: AppReviewRequester = get()
){
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        appReviewRequester.request(context = context as ComponentActivity)
    }

   BlurredBackground {
        val number by offerViewModel.displayNumber.collectAsState()
        Column(
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "$$number",
                fontSize = if (number.count() > 5) 55.sp else 75.sp,
                color = Color.White
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                offerViewModel.offerKeypad.forEach { row ->
                    Spacer(modifier = Modifier.height(10.dp))
                    Row {
                        row.forEach { button ->
                            Spacer(modifier = Modifier.width(10.dp))
                            IconButton(
                                onClick = { offerViewModel.processInput(button) },
                                enabled = offerViewModel.isKeypadEnabled || offerViewModel.controlKeys.contains(button)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Box(
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(CircleShape)
                                    )
                                    Text(
                                        text = button.toString(),
                                        fontSize = 40.sp,
                                        color = Color.White
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))

                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    enabled = offerViewModel.number.value.toFloat() > 0.50f,
                    onClick = {
                        Log.d("OFFER", "${offerViewModel.number.value.toFloat()}" )
                        if (authViewModel.currentUser?.isGuest == true) {
                            navController.navigate(NavigationRoutes.AuthControllerScreen.route)
                        } else {
                            navController.navigate("pay/${offerViewModel.number.value.toFloat()}")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(55.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(C.MOMENTUM_ORANGE),
                        disabledBackgroundColor = Color(C.MOMENTUM_ORANGE).copy(alpha = 0.55f)
                    )
                ) {
                    Text(
                        text = "Offer",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                BottomSpacing()
            }
        }
    }
}

@Composable
@Preview
fun OfferScreenPreview(){
    OfferScreen(rememberNavController() ,offerViewModel = getViewModel(), authViewModel = getViewModel())
}
