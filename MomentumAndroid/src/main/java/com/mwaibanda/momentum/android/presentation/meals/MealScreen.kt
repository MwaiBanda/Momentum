package com.mwaibanda.momentum.android.presentation.meals

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.domain.models.Meal
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

@Composable
fun MealScreen(
    mealViewModel: MealViewModel,
    channel: Channel<Meal>,
    onMealSelected: (Meal?) -> Unit,
    onShowModal: () -> Unit
) {
    var meals by remember {
        mutableStateOf(emptyList<Meal>())
    }
    LaunchedEffect(key1 = Unit){
        mealViewModel.getMeals {
            meals = it
        }
        launch {
            for (meal in channel) {
                mealViewModel.getMeals {
                    meals = it
                }
            }
        }
    }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Column {
            Column {
                Spacer(modifier = Modifier.height(65.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Meals",
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(10.dp)
                    )

                    IconButton(onClick = onShowModal) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Meal Icon",
                            tint = Color(C.MOMENTUM_ORANGE)
                        )
                    }
                }
                Divider()
                Text(
                    text = "View meals available & volunteer".uppercase(),
                    modifier = Modifier.padding(horizontal = 10.dp),
                    style = MaterialTheme.typography.caption,
                    color = Color(C.MOMENTUM_ORANGE)
                )
                Column(
                    Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                ) {
                    meals.forEach {
                        DescriptionCard(title = it.recipient, description = it.reason) {
                            onMealSelected(it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DescriptionCard(title: String, description: String, onCardClicked: () -> Unit) {
    Card(
        Modifier
            .heightIn(min = 55.dp)
            .fillMaxWidth(0.97f)
            .clip(RoundedCornerShape(5.dp))
            .padding(top = 10.dp, start = 10.dp)
            .padding(1.dp)
            .clickable { onCardClicked() }
    ) {
        Column(Modifier.padding((8.7).dp)) {
            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = description,
                        color = Color.Gray,
                        fontSize = 13.sp,
                    )
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Profile navigation icon",
                    tint = Color(0xFF434359)
                )
            }
        }
    }
}