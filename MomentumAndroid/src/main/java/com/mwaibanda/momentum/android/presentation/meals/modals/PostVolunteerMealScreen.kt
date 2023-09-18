package com.mwaibanda.momentum.android.presentation.meals.modals

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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.core.utils.getDate
import com.mwaibanda.momentum.android.presentation.components.BaseModal
import com.mwaibanda.momentum.android.presentation.components.IconTextField
import com.mwaibanda.momentum.domain.models.VolunteeredMeal

@Composable
fun PostVolunteerMealScreen(volunteeredMeal: VolunteeredMeal, closeModal: () -> Unit){
    BaseModal(closeModal, arrangement = Arrangement.SpaceBetween) {
        val configuration = LocalConfiguration.current
        Card(
            Modifier
                .size((configuration.screenWidthDp - 30).dp, 590.dp)
                .padding(bottom = 20.dp),
            elevation = 4.dp
        ) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            Modifier.padding(
                                horizontal = 15.dp,
                                vertical = 25.dp
                            )
                        ) {
                            Text(
                                text = "Let's Get Started",
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Gray
                            )
                            Text(
                                text = "With this new meal",
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                        }
                        Text(
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(fontSize = 11.sp)) {
                                    append("1")
                                }
                                withStyle(SpanStyle(fontSize = 16.sp)) {
                                    append("/")
                                }
                                withStyle(SpanStyle(fontSize = 11.sp)) {
                                    append("1")
                                }

                            },
                            modifier = Modifier.padding(end = 20.dp),
                            color = Color.Gray
                        )

                    }

                    Divider()
                }


                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                        .verticalScroll(rememberScrollState())
                ) {
                    IconTextField(
                        text = TextFieldValue(getDate(volunteeredMeal.date)),
                        placeholder = "Date",
                        icon = Icons.Outlined.CalendarMonth,
                        keyboardType = KeyboardType.Text,
                        accentColor = Color.Gray,
                        isReadOnly = true
                    )
                    Divider()
                    Text(
                        text = "Meal",
                        modifier = Modifier.padding(horizontal = 10.dp),
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
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
                    Text(
                        text = "Additional Notes",
                        modifier = Modifier.padding(horizontal = 10.dp),
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
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

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Divider()
                    Button(
                        onClick = {

                        },
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .fillMaxWidth(0.9f)
                            .height(45.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(C.MOMENTUM_ORANGE),
                            disabledBackgroundColor = Color(C.MOMENTUM_ORANGE).copy(
                                alpha = 0.55f
                            )
                        )
                    ) {
                        Text(
                            text = "Volunteer",
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