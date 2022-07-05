package com.mwaibanda.momentum.android.presentation.offer

import androidx.compose.foundation.background
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
import com.mwaibanda.momentum.android.presentation.components.BlurredBackground

@Composable
fun OfferScreen(offerViewModel: OfferViewModel){
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
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(55.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE55F1F))
                ) {
                    Text(
                        text = "Offer",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
@Preview
fun OfferScreenPreview(){
    OfferScreen(offerViewModel = viewModel())
}
