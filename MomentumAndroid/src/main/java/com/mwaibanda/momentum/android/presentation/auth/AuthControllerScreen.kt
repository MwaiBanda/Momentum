package com.mwaibanda.momentum.android.presentation.auth

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.core.utils.Constants
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
    val animatedSizeDp: Dp by animateDpAsState(
        targetValue = if (showSignUp) 430.dp else 260.dp
    )
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
                    Box(
                        Modifier
                            .padding(10.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .size(30.dp)
                    ) {
                        IconButton(onClick = onCloseModal) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close transaction icon",
                                tint = Color.LightGray
                            )
                        }
                    }
                }
                Column(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(Modifier.height(10.dp))

                    TranslucentBackground(
                        Modifier.size(
                            height = animatedSizeDp,
                            width = screenWidth - 50.dp
                        )
                    ) {
                        Crossfade(targetState = showSignUp) { showSignUp ->
                            if (showSignUp) {
                                SignUpScreen(authViewModel = authViewModel, profileViewModel = profileViewModel) {
                                    onCloseModal()
                                }
                            } else {
                                SignInScreen(authViewModel = authViewModel) {
                                    onCloseModal()
                                }
                            }
                        }
                    }
                    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {


                        TranslucentBackground(
                            Modifier
                                .size(
                                    height = 45.dp,
                                    width = screenWidth - 50.dp
                                )
                                .clip(RoundedCornerShape(15.dp))
                                .clickable { showSignUp = showSignUp.not() }
                        ) {
                            Column(
                                Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 10.dp), verticalArrangement = Arrangement.Center
                            ) {

                               Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                   Text(
                                       text = buildAnnotatedString {
                                           append("${ if (showSignUp) "Already" else "Don't" } have an account? ")
                                           withStyle(
                                               style = SpanStyle(
                                                   color = Color(Constants.MOMENTUM_ORANGE),
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
                        Spacer(Modifier.height(10.dp))
                        Spacer(
                            Modifier
                                .windowInsetsBottomHeight(WindowInsets.navigationBars)
                                .fillMaxWidth())                    }
                }
            }
        }
    }
}