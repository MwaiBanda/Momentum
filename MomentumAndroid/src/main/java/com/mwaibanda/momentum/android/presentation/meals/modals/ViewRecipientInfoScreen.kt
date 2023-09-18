package com.mwaibanda.momentum.android.presentation.meals.modals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.PinDrop
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.outlined.Timelapse
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.presentation.components.BaseModal
import com.mwaibanda.momentum.android.presentation.components.RecipientInfo
import com.mwaibanda.momentum.domain.models.Meal

@Composable
fun ViewRecipientInfoScreen(currentMeal: Meal?, closeModal: () -> Unit) {
    BaseModal(closeModal, "Recipient Information") {
        Column {
            Divider()
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .background(Color(0xFFFFEFC1))
                        .padding(horizontal = 10.dp)
                        .padding(top = 20.dp, bottom = 10.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    RecipientInfo(
                        title = "Allergies or Restrictions",
                        description = "None",
                        icon = Icons.Outlined.Shield
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    RecipientInfo(
                        title = "Meal Drop-Off Location",
                        description = """
                        ${currentMeal?.street ?: ""}
                        ${currentMeal?.city ?: ""}
                        ${currentMeal?.phone ?: ""}
                        """.trimIndent(),
                        icon = Icons.Outlined.PinDrop
                    )
                    RecipientInfo(
                        title = "Drop-time",
                        description = currentMeal?.preferredTime ?: "",
                        icon = Icons.Outlined.Timelapse
                    )

                    RecipientInfo(
                        title = "Food for",
                        description = "${currentMeal?.numOfAdults ?: 0} Adults, ${currentMeal?.numberOfKids} Kids",
                        icon = Icons.Outlined.People
                    )

                    RecipientInfo(
                        title = "Favourite Meals or Restaurants",
                        description = currentMeal?.favourites ?: "",
                        icon = Icons.Outlined.ThumbUp
                    )
                }
            }
        }
    }
}
