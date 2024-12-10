package com.mwaibanda.momentum.android.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.presentation.components.IconTextField
import com.mwaibanda.momentum.android.presentation.components.PasswordTextField

@Composable
fun SignInScreen(
    showResetPassword: Boolean,
    authViewModel: AuthViewModel,
    onFocusChange: (Boolean) -> Unit,
    onCloseModal: () -> Unit
) {
    val context = LocalContext.current
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
                text = if (showResetPassword) "Reset Password" else "Sign In",
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                style = MaterialTheme.typography.h6
            )
            Text(
                text = if (showResetPassword) "Enter Email to Get a Password Reset Link" else "Sign In To Your Account To Proceed",
                color = Color.White.copy(0.3f),
                style = MaterialTheme.typography.caption
            )
        }
        Divider(color = Color.White.copy(0.5f), thickness = 0.8.dp)
        IconTextField(
            text = email,
            placeholder = "Email",
            icon = Icons.Outlined.MailOutline,
            keyboardType = KeyboardType.Email,
            onTextChange = { email = it }
        ) {
            focusManager.clearFocus()
        }
        Divider(color = Color.White.copy(0.5f), thickness = 0.8.dp)
        if (showResetPassword.not()) {
            PasswordTextField(
                text = password,
                placeholder = "Password",
                onTextChange = { password = it },
                onFocusChange = onFocusChange
            ) {
                focusManager.clearFocus()
            }
            Divider(color = Color.White.copy(0.5f), thickness = 0.8.dp)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = {
                    if (email.text.isNotEmpty()) {
                        if (showResetPassword) {
                            authViewModel.resetPassword(email = email.text) {
                                Toast.makeText(
                                    context,
                                    "Please check your email for a password reset link",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            if (password.text.isNotEmpty()) {
                                authViewModel.signIn(email = email.text, password = password.text) {
                                    onCloseModal()
                                }
                            }
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
                    text = if (showResetPassword) "Reset Password" else "Sign In",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        }
    }
}