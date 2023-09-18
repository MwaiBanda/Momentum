package com.mwaibanda.momentum.android.presentation.meals

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Timelapse
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.core.utils.Modal
import com.mwaibanda.momentum.android.core.utils.getDate
import com.mwaibanda.momentum.android.presentation.components.RecipientInfo
import com.mwaibanda.momentum.domain.models.Meal
import com.mwaibanda.momentum.domain.models.VolunteeredMeal

@Composable
fun MealsDetailScreen(meal: Meal, onShowModal: (Modal, VolunteeredMeal?) -> Unit) {
    Column(
        Modifier
            .systemBarsPadding()
            .padding(top = 50.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            Modifier
                .heightIn(min = 55.dp)
                .fillMaxWidth(0.97f)
                .clip(RoundedCornerShape(5.dp))
                .padding(top = 10.dp, start = 10.dp)
                .padding(1.dp)
                .clickable { }
        ) {
            Column(Modifier.padding(10.dp)) {
                Text(
                    text = "Recipient",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
                RecipientInfo(
                    title = "Allergies or Restrictions",
                    description = "None",
                    icon = Icons.Outlined.Shield
                )
                RecipientInfo(
                    title = "Food for",
                    description = "${meal.numOfAdults} Adults, ${meal.numberOfKids} Kids",
                    icon = Icons.Outlined.People
                )
                RecipientInfo(
                    title = "Drop-time",
                    description = meal.preferredTime,
                    icon = Icons.Outlined.Timelapse
                )
                OutlinedButton(
                    onClick = {
                        onShowModal(Modal.ViewRecipientInfo, null)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(2.dp, Color(C.MOMENTUM_ORANGE)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(C.MOMENTUM_ORANGE),
                    )
                ) {
                    Text(
                        text = "View All Recipient Info",
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
            }
        }
        Text(
            text = "Meal Calendar",
            modifier = Modifier
                .padding(vertical = 15.dp)
                .padding(start = 10.dp),
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
        meal.meals.sortedBy { getDate(it.date) }.forEach {
            Card(
                Modifier
                    .heightIn(min = 55.dp)
                    .fillMaxWidth(0.97f)
                    .clip(RoundedCornerShape(5.dp))
                    .padding(top = 10.dp, start = 10.dp)
                    .padding(1.dp)
                    .clickable { }
            ) {
                Row(Modifier.padding(10.dp)) {
                    Column {
                        Text(
                            text = getDate(it.date).split(",").first().trim(),
                            fontWeight = FontWeight.Medium
                        )
                        Text(text = getDate(it.date).split(",").last().trim())
                    }

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .height(45.dp)
                            .background(Color.Gray.copy(0.5f))
                            .width(1.dp)
                    )
                    if (it.description.isEmpty()) {
                        Column {
                            Text(
                                text = "Available",
                                fontWeight = FontWeight.Medium,
                            )
                            Button(
                                onClick = {
                                    onShowModal(Modal.PostVolunteerMeal, it)
                                },
                                elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(35.dp),
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(2.dp, Color(C.MOMENTUM_ORANGE)),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    backgroundColor = Color(C.MOMENTUM_ORANGE),
                                )
                            ) {
                                Text(
                                    text = "Volunteer",
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White
                                )
                            }
                        }
                    } else {
                        Box(
                            Modifier
                                .clip(CircleShape)
                                .size(50.dp)
                                .background(Color(C.MOMENTUM_ORANGE))
                                .padding(5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = it.user.fullname
                                    .split(" ")
                                    .map { it.firstOrNull().toString() }
                                    .reduce { x, y -> x + y },
                                fontWeight = FontWeight.ExtraBold,
                                style = MaterialTheme.typography.h6,
                                color = Color.White
                            )
                        }
                    }

                    Column(Modifier.padding(start = 10.dp)) {
                        Text(text = it.user.fullname, fontWeight = FontWeight.Medium)
                        Text(text = it.description.trim())
                    }
                }
            }
        }
    }
}



