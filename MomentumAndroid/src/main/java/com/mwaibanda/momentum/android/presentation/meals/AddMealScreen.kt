package com.mwaibanda.momentum.android.presentation.meals

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mwaibanda.momentum.android.R
import com.mwaibanda.momentum.android.core.utils.C

@Composable
fun AddMealScreen(closeModal: () -> Unit) {
    val configuration = LocalConfiguration.current
    var currentTab by remember {
        mutableIntStateOf(0)
    }
    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.post_success)
    )
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 2,
        isPlaying = currentTab == 4,
        speed = 1f,
        restartOnPlay = false

    )
    BackHandler {
        closeModal()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.93f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            IconButton(onClick = closeModal) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close transaction icon")
            }
        }
        Card(Modifier.size((configuration.screenWidthDp - 30).dp, 540.dp)) {
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Column(Modifier.padding(horizontal = 15.dp, vertical = 25.dp)) {
                        Text(text = "Let's Get Started")
                        Text(text = "With this new meal")
                    }
                    Divider()
                }
                when (currentTab) {
                    0 -> Column(Modifier.fillMaxSize(0.8f)) {
                        Text(text = "1")
                    }

                    1 -> Column(Modifier.fillMaxSize(0.8f)) {
                        Text(text = "2")
                    }

                    2 -> Column(Modifier.fillMaxSize(0.8f)) {
                        Text(text = "3")
                    }
                    3 -> Column(Modifier.fillMaxSize(0.8f)) {
                        Text(text = "4")
                    }
                    4 -> Column(
                        Modifier.fillMaxWidth().fillMaxHeight(0.8f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        LottieAnimation(
                            composition = composition,
                            progress = progress,
                            modifier = Modifier.size(200.dp)
                        )

                        Text(
                            text = """
                               Your payment has Your Meal has been posted
                               successfully. For more details. Check the Meals
                               section to see or edit previous meals,
                               in the Meals tab.
                               """.trimIndent().trim(),
                            textAlign = TextAlign.Center,

                        )
                    }
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Divider()
                    Button(
                        onClick = {
                            if (currentTab < 4) {
                                currentTab++
                            }else  {
                                closeModal()
                                currentTab = 0
                            }
                        },
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .fillMaxWidth(0.9f)
                            .height(45.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(C.MOMENTUM_ORANGE),
                            disabledBackgroundColor = Color(C.MOMENTUM_ORANGE).copy(alpha = 0.55f)
                        )
                    ) {
                        Text(
                            text = if (currentTab < 3) "Next" else "Dismiss",
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                }


            }

        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}