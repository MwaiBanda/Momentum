package com.mwaibanda.momentum.android.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.core.utils.Constants

@Composable
fun ToggleAmountLabel(
    title: String,
    amount: String,
    isSelected: Boolean,
    isDisabled:  () -> Boolean,
    showLabels: Boolean,
    onToggleClick: (Boolean) -> Unit,
    onAmountChange: (String) -> Unit,
    onAmountCommit: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 20.dp)
            .padding(vertical = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {


        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                Modifier
                    .clip(CircleShape)
                    .size(25.dp)
                    .clickable(enabled = !isDisabled()) {
                        onToggleClick(isSelected)
                    }
                    .border(
                        2.dp,
                        if (isSelected) Color(Constants.MOMENTUM_ORANGE) else Color.Gray,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier
                        .clip(CircleShape)
                        .size(15.dp)
                        .background(if (isSelected) Color(Constants.MOMENTUM_ORANGE) else Color.Gray)
                        .padding(5.dp)
                )

            }
            Text(
                text = title,
                Modifier.padding(start = 10.dp),
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.body1,
            )

        }

        Row {
            if (showLabels) {
                Text(
                    text = "amount: ",
                    color = Color.Gray,
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.subtitle2,
                )
                Text(
                    text = "$",
                    style = MaterialTheme.typography.subtitle2,
                )
                BasicTextField(
                    value = amount,
                    textStyle = MaterialTheme.typography.subtitle2,
                    onValueChange = onAmountChange,
                    modifier = Modifier.width(IntrinsicSize.Min),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                        onAmountCommit(amount)
                    }),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
                )
            }
        }
    }
}

@Composable
@Preview
fun ToggleAmountLabelPreview() {
    var isSelected by remember {
        mutableStateOf(false)
    }
    ToggleAmountLabel(
        title = "Offering",
        amount = "$0",
        isSelected = isSelected,
        isDisabled = { true },
        showLabels = true,
        onToggleClick = {isSelected = it.not()},
        onAmountChange = {}
    ) {

    }
}
