package com.mwaibanda.momentum.android.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BasePlainExpandableCard(
    isExpanded: Boolean,
    contentHeight: Int,
    showCoverDivider: Boolean,
    coverContent: @Composable () -> Unit,
    onCoverClick: () -> Unit,
    coverIcon: @Composable (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    val animatedSizeDp: Dp by animateDpAsState(
        targetValue = if (isExpanded) contentHeight.dp else 0.dp
    )
    Box {
        AnimatedVisibility(visible = isExpanded, enter = fadeIn(initialAlpha = 0.3f), exit = fadeOut() ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .height(animatedSizeDp)
        ) {
                    Spacer(modifier = Modifier.height(70.dp))
                    content()

            }
        }
        Column(Modifier.fillMaxWidth()) {
            if (showCoverDivider) Divider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .height(60.dp)
                    .clickable { onCoverClick() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    coverContent()
                }

                coverIcon(isExpanded)

            }
            Divider()
        }
    }
}

@Preview
@Composable
fun BasePlainExpandableCardPreview() {
    var isContactExpanded by rememberSaveable { mutableStateOf(false) }
    var isBillingExpanded by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier
            .background(Color.White)
            .padding(vertical = 20.dp)
    ) {
        BasePlainExpandableCard(
            isExpanded = isContactExpanded,
            contentHeight = 200,
            showCoverDivider = true,
            coverContent = {
                Icon(imageVector = Icons.Default.Person, contentDescription = "Expand Icon")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Contact Information")
            },
            onCoverClick = {
                isContactExpanded = isContactExpanded.not()
                isBillingExpanded = false
            },
            coverIcon = { isExpanded ->

                Icon(
                    imageVector = if (isExpanded)
                        Icons.Default.ArrowDropUp
                    else
                        Icons.Default.ArrowDropDown,
                    contentDescription = "Expand Icon"
                )

            }
        ) {
            Text(text = "Contact Content")
        }
        BasePlainExpandableCard(
            isExpanded = isBillingExpanded,
            contentHeight = 300,
            showCoverDivider = isContactExpanded,
            coverContent = {
                Icon(imageVector = Icons.Default.Domain, contentDescription = "Expand Icon")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Billing Information")
            },
            onCoverClick = {
                isBillingExpanded = isBillingExpanded.not()
                isContactExpanded = false
            },
            coverIcon = { isExpanded ->
                Icon(
                    imageVector = if (isExpanded)
                        Icons.Default.ArrowDropUp
                    else
                        Icons.Default.ArrowDropDown,
                    contentDescription = "Expand Icon"
                )

            }
        ) {
            Text(text = "Billing Content")
        }
    }
}