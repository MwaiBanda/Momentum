package com.mwaibanda.momentum.android.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.presentation.components.IconTextfield
import com.mwaibanda.momentum.android.presentation.components.PasswordTextField

@Composable
fun SignInScreen(
    authViewModel: AuthViewModel,
    onFocusChange: (Boolean) -> Unit,
    onCloseModal: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var email by remember {
        mutableStateOf(TextFieldValue())
    }
    var password by remember {
        mutableStateOf(TextFieldValue())
    }

    var passwordVisibility by remember { mutableStateOf(false) }
    Column {
        Column(Modifier.padding(10.dp)) {
            Text(
                text = "Sign In",
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                style = MaterialTheme.typography.h6
            )
            Text(
                text = "Sign In To Your Account To Proceed",
                color = Color.White.copy(0.3f),
                style = MaterialTheme.typography.caption
            )
        }
        Divider(color = Color.White.copy(0.5f), thickness = 0.8.dp)
        IconTextfield(
            text = email,
            placeholder = "Email",
            icon = Icons.Outlined.MailOutline,
            keyboardType = KeyboardType.Email,
            onTextChange = { email = it }
        ) {
            focusManager.clearFocus()
        }
        Divider(color = Color.White.copy(0.5f), thickness = 0.8.dp)
        PasswordTextField(
            text = password,
            placeholder = "Password",
            onTextChange = { password = it },
            onFocusChange = onFocusChange
        ) {
            focusManager.clearFocus()
        }
        Divider(color = Color.White.copy(0.5f), thickness = 0.8.dp)
        Spacer(modifier = Modifier.height(10.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = {
                    if (email.text.isNotEmpty() and password.text.isNotEmpty()) {
                        authViewModel.signIn(email = email.text, password = password.text) {
                            onCloseModal()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(55.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(C.MOMENTUM_ORANGE))
            ) {
                Text(
                    text = "Sign In",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        }
    }
}