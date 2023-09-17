package com.mwaibanda.momentum.android.presentation.meals

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.domain.models.Meal

@Composable
fun MealsDetailScreen(meal: Meal) {
    Column(
        Modifier
            .systemBarsPadding()
            .padding(top = 65.dp)) {
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
                Text(text = "Recipient", style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold)
            }
        }
        Text(text = "Meal Calendar", modifier = Modifier
            .padding(vertical = 20.dp)
            .padding(start = 10.dp), style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold)
        meal.meals.forEach {
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
                    Text(text = it.date)
                    Divider()
                    Column {
                        Text(text = it.user.fullname)
                        Text(text = it.description)
                    }
                }
            }
        }
    }
}