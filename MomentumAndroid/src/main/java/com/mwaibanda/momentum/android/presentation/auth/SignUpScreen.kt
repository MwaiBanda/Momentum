package com.mwaibanda.momentum.android.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.presentation.components.IconTextField
import com.mwaibanda.momentum.android.presentation.components.PasswordTextField
import com.mwaibanda.momentum.android.presentation.profile.ProfileViewModel

@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel,
    onFocusChange: (Boolean) -> Unit,
    onCloseModal: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var fullname by remember {
        mutableStateOf(TextFieldValue())
    }
    var phone by remember {
        mutableStateOf(TextFieldValue())
    }
    var email by remember {
        mutableStateOf(TextFieldValue())
    }
    var password by remember {
        mutableStateOf(TextFieldValue())
    }
    var confirmPassword by remember {
        mutableStateOf(TextFieldValue())
    }
    Column {
        Column(Modifier.padding(10.dp)) {
            Text(
                text = "Sign Up",
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                style = MaterialTheme.typography.h6
            )
            Text(
                text = "Create An Account To Proceed",
                color = Color.White.copy(0.3f),
                style = MaterialTheme.typography.caption
            )
        }
        Divider(color = Color.White.copy(0.5f), thickness = 0.8.dp)
        IconTextField(
            text = fullname,
            placeholder = "Fullname",
            icon = Icons.Outlined.Person,
            keyboardType = KeyboardType.Email,
            onTextChange = { fullname = it }
        ) {
            focusManager.clearFocus()
        }
        Divider(color = Color.White.copy(0.5f), thickness = 0.8.dp)
        IconTextField(
            text = phone,
            placeholder = "Phone",
            icon = Icons.Outlined.Phone,
            keyboardType = KeyboardType.Email,
            onTextChange = { phone = it }
        ) {
            focusManager.clearFocus()
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
        PasswordTextField(
            text = password,
            placeholder = "Password",
            onTextChange = { password = it },
            onFocusChange = onFocusChange
        ) {
            focusManager.clearFocus()
        }
        Divider(color = Color.White.copy(0.5f), thickness = 0.8.dp)
        PasswordTextField(
            text = confirmPassword,
            placeholder = "Confirm Password",
            onTextChange = { confirmPassword = it },
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
                        authViewModel.signUp(email = email.text, password = password.text) {
                            profileViewModel.addUser(
                                fullname = fullname.text,
                                phone = phone.text,
                                password = password.text,
                                email = email.text,
                                createdOn = authViewModel.getCurrentDate(),
                                userId =  authViewModel.currentUser?.id ?: ""
                            )
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
                    text = "Sign Up",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        }
    }
}