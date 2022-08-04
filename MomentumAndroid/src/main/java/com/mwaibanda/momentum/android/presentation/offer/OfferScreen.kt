package com.mwaibanda.momentum.android.presentation.offer

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mwaibanda.momentum.android.core.utils.Constants
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes
import com.mwaibanda.momentum.android.presentation.auth.AuthViewModel
import com.mwaibanda.momentum.android.presentation.components.BlurredBackground
import com.mwaibanda.momentum.android.presentation.components.BottomSpacing
import org.koin.androidx.compose.getViewModel

@Composable
fun OfferScreen(
    navController: NavController,
    offerViewModel: OfferViewModel = getViewModel(),
    authViewModel: AuthViewModel
){
   BlurredBackground {
        var number by remember {
            mutableStateOf("0")
        }
        offerViewModel.displayNumber.observe(LocalLifecycleOwner.current) {
            number = it
        }
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
                    onClick = {
                        Log.d("OFFER", "${offerViewModel.number.value?.toFloat()}" )
                        if (authViewModel.user?.isGuest == true) {
                            navController.navigate(NavigationRoutes.AuthControllerScreen.route)
                        } else {
                            navController.navigate("pay/${offerViewModel.number.value?.toFloat()}")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(55.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(Constants.MOMENTUM_ORANGE))
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
