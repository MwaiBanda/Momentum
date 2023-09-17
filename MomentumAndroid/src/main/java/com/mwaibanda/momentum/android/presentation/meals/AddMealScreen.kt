package com.mwaibanda.momentum.android.presentation.meals

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.House
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mwaibanda.momentum.android.R
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.presentation.components.IconTextField
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode

@Composable
fun AddMealScreen(closeModal: () -> Unit) {
    val configuration = LocalConfiguration.current
    var currentTab by remember {
        mutableIntStateOf(0)
    }
    val calendarState = rememberSelectableCalendarState(
        initialSelectionMode = SelectionMode.Multiple
    )
    val composition by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.post_success)
    )
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 2,
        isPlaying = currentTab == 4,
        speed = 1f,
        restartOnPlay = true

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
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close add meals icon")
            }
        }
        Card(
            Modifier
                .size((configuration.screenWidthDp - 30).dp, 540.dp)
                .padding(bottom = 20.dp), elevation = 4.dp) {
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.padding(horizontal = 15.dp, vertical = 25.dp)) {
                            Text(text = "Let's Get Started", style = MaterialTheme.typography.h6, fontWeight = FontWeight.ExtraBold, color = Color.Gray)
                            Text(text = "With this new meal",  fontWeight = FontWeight.Bold, color = Color.Gray)
                        }
                        Text(text = buildAnnotatedString {
                            withStyle(SpanStyle(fontSize = 11.sp)) {
                                append("${currentTab + 1}")
                            }
                            withStyle(SpanStyle(fontSize = 16.sp)) {
                                append("/")
                            }
                            withStyle(SpanStyle(fontSize = 11.sp)) {
                                append("5")
                            }

                        }, modifier = Modifier.padding(end = 20.dp), color = Color.Gray)

                    }

                    Divider()
                }
                AnimatedContent(
                    targetState = currentTab,
                    transitionSpec = {
                        if (targetState > initialState) {
                            (slideInHorizontally { height -> height } + fadeIn()).togetherWith(
                                slideOutHorizontally { height -> -height } + fadeOut())
                        } else {

                            (slideInHorizontally { height -> -height } + fadeIn()).togetherWith(
                                slideOutHorizontally { height -> height } + fadeOut())
                        }.using(SizeTransform(clip = false))
                    }, label = "Slide in Meal Card Content"
                ) { targetCount ->
                    when (targetCount) {
                        0 -> Column(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.8f)
                        ) {
                            IconTextField(
                                text = TextFieldValue(),
                                placeholder = "Recipient",
                                icon = Icons.Outlined.Person,
                                keyboardType = KeyboardType.Text,
                                accentColor = Color.Gray,
                                onTextChange = {}
                            ) {

                            }
                            Divider()
                            IconTextField(
                                text = TextFieldValue(),
                                placeholder = "Email",
                                icon = Icons.Outlined.Mail,
                                keyboardType = KeyboardType.Text,
                                accentColor = Color.Gray,
                                onTextChange = {}
                            ) {

                            }
                            Divider()
                            IconTextField(
                                text = TextFieldValue(),
                                placeholder = "Phone",
                                icon = Icons.Outlined.Phone,
                                keyboardType = KeyboardType.Text,
                                accentColor = Color.Gray,
                                onTextChange = {}
                            ) {

                            }
                            Divider()
                            IconTextField(
                                text = TextFieldValue(),
                                placeholder = "Reason",
                                icon = Icons.Outlined.Info,
                                keyboardType = KeyboardType.Text,
                                accentColor = Color.Gray,
                                onTextChange = {}
                            ) {

                            }
                        }

                        1 -> Column(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.8f)
                                .verticalScroll(rememberScrollState())
                        ) {
                            SelectableCalendar(
                                calendarState = calendarState
                            )
                        }

                        2 -> Column(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.8f)
                        ) {
                            IconTextField(
                                text = TextFieldValue(),
                                placeholder = "Street",
                                icon = Icons.Outlined.House,
                                keyboardType = KeyboardType.Text,
                                accentColor = Color.Gray,
                                onTextChange = {}
                            ) {

                            }
                            Divider()
                            IconTextField(
                                text = TextFieldValue(),
                                placeholder = "City",
                                icon = Icons.Outlined.LocationCity,
                                keyboardType = KeyboardType.Text,
                                accentColor = Color.Gray,
                                onTextChange = {}
                            ) {

                            }
                            Divider()
                            IconTextField(
                                text = TextFieldValue(),
                                placeholder = "Number of Adults",
                                icon = Icons.Outlined.Person,
                                keyboardType = KeyboardType.Text,
                                accentColor = Color.Gray,
                                onTextChange = {}
                            ) {

                            }
                            Divider()
                            IconTextField(
                                text = TextFieldValue(),
                                placeholder = "Number of Kids",
                                icon = Icons.Outlined.Person,
                                keyboardType = KeyboardType.Text,
                                accentColor = Color.Gray,
                                onTextChange = {}
                            ) {

                            }
                            Divider()
                            IconTextField(
                                text = TextFieldValue(),
                                placeholder = "Preferred Delivery Time(4pm - 5pm)",
                                icon = Icons.Outlined.CalendarMonth,
                                keyboardType = KeyboardType.Text,
                                accentColor = Color.Gray,
                                onTextChange = {}
                            ) {

                            }
                        }

                        3 -> Column(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.8f)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(text = "Favorite Meals or Restaurants", modifier = Modifier.padding(horizontal = 10.dp),  fontWeight = FontWeight.Bold, color = Color.Gray)
                            BasicTextField(
                                value = "",
                                onValueChange = { },
                                minLines = 5,
                                maxLines = 5,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(horizontal = 10.dp)
                            )
                            Divider()
                            Text(text = "Least Favourite Meals", modifier = Modifier.padding(horizontal = 10.dp),  fontWeight = FontWeight.Bold, color = Color.Gray)
                            BasicTextField(
                                value = "",
                                onValueChange = { },
                                minLines = 5,
                                maxLines = 5,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(horizontal = 10.dp)
                            )
                            Divider()
                            Text(text = "Allergies or Dietary Restrictions", modifier = Modifier.padding(horizontal = 10.dp),  fontWeight = FontWeight.Bold, color = Color.Gray)
                            BasicTextField(
                                value = "",
                                onValueChange = { },
                                minLines = 5,
                                maxLines = 5,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(horizontal = 10.dp)
                            )
                            Divider()
                            Text(text = "Additional Special Instructions", modifier = Modifier.padding(horizontal = 10.dp),  fontWeight = FontWeight.Bold, color = Color.Gray)
                            BasicTextField(
                                value = "",
                                onValueChange = { },
                                minLines = 5,
                                maxLines = 5,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(horizontal = 10.dp)
                            )
                        }

                        4 -> Column(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.8f),
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
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Divider()
                    Button(
                        onClick = {
                            if (currentTab < 4) {
                                currentTab++
                            } else {
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