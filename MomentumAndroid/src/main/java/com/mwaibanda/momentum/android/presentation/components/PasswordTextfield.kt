package com.mwaibanda.momentum.android.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.core.utils.C

@Composable
fun PasswordTextField(
    text: TextFieldValue,
    placeholder: String,
    onTextChange: (TextFieldValue) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    onCommit: () -> Unit
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    TextField(
        modifier = Modifier.fillMaxWidth()
            .onFocusChanged {
                if (it.isFocused) {
                    onFocusChange(false)
                } else {
                    onFocusChange(true)
                }
            },
        value = text,
        onValueChange = onTextChange,
        shape = RoundedCornerShape(10.dp),
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
        label = {
            Text(text = placeholder)
        },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(onDone = {
              onCommit()
        }),
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Lock,
                contentDescription = "",
                tint = Color.White
            )
        },
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(
                    imageVector = if (passwordVisibility)Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    )
}