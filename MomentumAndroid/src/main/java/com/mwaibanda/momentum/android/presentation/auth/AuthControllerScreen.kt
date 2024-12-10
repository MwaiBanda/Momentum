package com.mwaibanda.momentum.android.presentation.auth

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.presentation.components.BlurredBackground
import com.mwaibanda.momentum.android.presentation.components.TranslucentBackground
import com.mwaibanda.momentum.android.presentation.profile.ProfileViewModel

@Composable
fun AuthControllerScreen(
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel,
    onCloseModal: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    var showSignUp by remember {
        mutableStateOf(true)
    }
    var showResetPassword by remember {
        mutableStateOf(false)
    }

    var isPasswordFocused by remember {
        mutableStateOf(false)
    }
    val animateAuthSizeDp: Dp by animateDpAsState(
        targetValue = if (showSignUp) 430.dp else if (showResetPassword) 200.dp else 260.dp, label = ""
    )


    BackHandler {
        onCloseModal()
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.95f)
    ) {
        BlurredBackground(hasHeaderPadding = false) {
            Column(Modifier.fillMaxHeight()) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Column(
                        Modifier
                            .padding(horizontal = 10.dp)
                            .padding(10.dp)
                            .clip(CircleShape)
                            .background(Color.Transparent) // Changed to white
                            .size(30.dp)
                    ) {
                        IconButton(onClick = { onCloseModal() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close auth icon",
                                tint = Color.LightGray
                            )
                        }
                    }
                }
                Column(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    if (isPasswordFocused) {
                        Spacer(Modifier.height(10.dp))
                    }
                    TranslucentBackground(
                        Modifier
                            .size(
                            height = animateAuthSizeDp,
                            width = screenWidth - 50.dp
                        )
                    ) {
                        Crossfade(targetState = showSignUp, label = "") { showSignUp ->
                            if (showSignUp) {
                                SignUpScreen(
                                    authViewModel = authViewModel,
                                    profileViewModel = profileViewModel,
                                    onFocusChange = { isPasswordFocused = it }
                                ) {
                                    onCloseModal()
                                }
                            } else {
                                SignInScreen(
                                    showResetPassword = showResetPassword,
                                    authViewModel = authViewModel,
                                    onFocusChange = { isPasswordFocused = it },
                                ) {
                                    onCloseModal()
                                }
                            }
                        }
                    }


                    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        if (showSignUp.not() && showResetPassword.not()) {
                            Column(
                                Modifier
                                    .clickable {
                                        showResetPassword = true
                                    }
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp), verticalArrangement = Arrangement.Center
                            ) {

                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                    Text(
                                        text = "Forgot Password?", color = Color.White
                                    )
                                    Icon(
                                        imageVector = Icons.Default.TouchApp,
                                        contentDescription = "",
                                        tint = Color.White,
                                    )
                                }
                            }
                        }

                        TranslucentBackground(
                            Modifier
                                .size(
                                    height = 45.dp,
                                    width = screenWidth - 50.dp
                                )
                                .clip(RoundedCornerShape(15.dp))
                                .clickable {
                                    if (showResetPassword) {
                                        showResetPassword = false
                                    } else {
                                        showSignUp = showSignUp.not()
                                    }
                                }
                        ) {
                            Column(
                                Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 10.dp), verticalArrangement = Arrangement.Center
                            ) {

                               Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                   Text(
                                       text = buildAnnotatedString {
                                           append("${ if (showSignUp || showResetPassword) "Already" else "Don't" } have an account? ")
                                           withStyle(
                                               style = SpanStyle(
                                                   color = Color(C.MOMENTUM_ORANGE),
                                                   fontWeight = FontWeight.Bold
                                               )
                                           ) {
                                               append("${ if (showSignUp) "Sign Up" else "Sign In" } here")

                                           }
                                       }, color = Color.White
                                   )
                                   Icon(
                                       imageVector = Icons.Default.TouchApp,
                                       contentDescription = "",
                                       tint = Color.White,
                                   )
                               }
                            }
                        }

                        Spacer(Modifier.height(100.dp))
                    }
                }
            }
        }
    }
}