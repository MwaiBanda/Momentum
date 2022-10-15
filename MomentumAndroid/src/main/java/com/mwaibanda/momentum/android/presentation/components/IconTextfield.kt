package com.mwaibanda.momentum.android.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.core.utils.C

@Composable
fun IconTextfield(
    text: TextFieldValue,
    placeholder: String,
    icon: ImageVector,
    keyboardType: KeyboardType,
    onTextChange: (TextFieldValue) -> Unit,
    onCommit: () -> Unit
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = Color(C.MOMENTUM_ORANGE),
            disabledLabelColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            focusedLabelColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedLabelColor = Color.White,
            textColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp),
        label = {
            Text(text = placeholder)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(onDone = {
            onCommit()
        }),
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "",
                tint = Color.White
            )
        }
    )
}

