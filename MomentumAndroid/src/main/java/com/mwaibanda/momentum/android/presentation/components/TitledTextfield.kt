package com.mwaibanda.momentum.android.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.core.utils.Constants

@Composable
fun TitleTextField(title: String, text: String, onTextChange: (String) -> Unit, onCommit: () -> Unit) {
    val focusManager = LocalFocusManager.current
    var isTyping by remember {
        mutableStateOf(false)
    }
    val password: String by lazy {
        var res = ""
        text.forEach {
            if(res.count() < text.count() - 3)
                res += "*"
            else
                res += it
        }
        res
    }

    Column(Modifier.padding(10.dp)) {

        Text(text = title.uppercase(), style = MaterialTheme.typography.caption, color = Color.Gray)
        Box() {
            Row(
                Modifier
                    .fillMaxWidth()
                    .offset(y = (-10).dp),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = if (isTyping) Icons.Default.Keyboard else Icons.Default.EditNote,
                    contentDescription = "",
                    tint = Color(Constants.MOMENTUM_ORANGE)
                )
            }
            BasicTextField(
                value = if (isTyping or text.isNotEmpty()) {
                    if (title == "password" && isTyping.not()) password else text }else "***",
                onValueChange = {
                    onTextChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .onFocusChanged {
                        if (it.isFocused) {
                            isTyping = true
                        }
                    },
                textStyle = if (text.isEmpty())
                    TextStyle(color = Color.Gray)
                else
                    MaterialTheme.typography.body1.copy(fontWeight = FontWeight.ExtraBold),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    isTyping = false
                    onCommit()
                }),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )

        }

    }
}

@Preview
@Composable
fun TitleTextFieldPreview() {
    Column(Modifier.background(Color.White)) {
        TitleTextField(title = "fullname", text = "", onTextChange = {}) {

        }
    }
}