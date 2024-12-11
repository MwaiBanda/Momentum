package com.mwaibanda.momentum.android.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mwaibanda.momentum.android.R
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.core.utils.NavigationRoutes
import com.mwaibanda.momentum.android.core.utils.ScreenConfiguration
import com.mwaibanda.momentum.android.presentation.message.MessageViewModel
import com.mwaibanda.momentum.domain.models.Message

@Composable
fun TopBar(navController: NavController, currentRoute: String?, messageViewModel: MessageViewModel, onShowModal: () -> Unit) {
    val showAddNote by messageViewModel.showAddNote.collectAsState()
    val currentMessage: Message? by messageViewModel.message.collectAsState()

    Box {
        Column {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {
                TopAppBar(
                    title = {
                        if (ScreenConfiguration.ScreensWithLogo.screens.contains(currentRoute))
                            Row(
                                Modifier.fillMaxWidth(0.9f),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.momentum_circle),
                                    contentDescription = "logo"
                                )
                            }

                    },
                    backgroundColor = if (ScreenConfiguration.ScreensWithoutBackButton.screens.contains(
                            currentRoute
                        )
                    ) Color.Transparent else Color.White,
                    elevation = 0.dp,
                    navigationIcon = {
                        if (ScreenConfiguration.ScreensWithoutBackButton.screens.contains(currentRoute)
                                .not()
                        ) {
                            IconButton(onClick = {
                                navController.popBackStack()
                            }, enabled = showAddNote.not()) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Filled.ArrowBackIosNew,
                                        "",
                                        tint = Color(C.MOMENTUM_ORANGE)
                                    )
                                    Text(
                                        text = "Back",
                                        color = Color(C.MOMENTUM_ORANGE)
                                    )
                                }
                            }

                        }

                        if (ScreenConfiguration.ScreensWithoutTopBarIcons.screens.contains(currentRoute)
                                .not()
                        ) {
                            IconButton(
                                onClick = {
                                    onShowModal()
                                },
                                enabled = ScreenConfiguration.ScreensWithoutBackButton.screens.contains(
                                    currentRoute
                                )
                            ) {
                                Icon(
                                    Icons.Outlined.AccessTime,
                                    contentDescription = "Transactions Icon",
                                    tint = Color.White,
                                    modifier = Modifier.size(35.dp)
                                )

                            }
                        }
                    },
                    actions = {
                        if (ScreenConfiguration.ScreensWithoutTopBarIcons.screens.contains(currentRoute)
                                .not()
                        )
                            IconButton(
                                onClick = { navController.navigate(NavigationRoutes.ProfileScreen.route) },
                                enabled = ScreenConfiguration.ScreensWithoutBackButton.screens.contains(
                                    currentRoute
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.AccountCircle,
                                    contentDescription = "Personal Info Icon",
                                    tint = Color.White,
                                    modifier = Modifier.size(35.dp)
                                )
                            }
                    },
                    modifier = Modifier.padding(
                        top = if (ScreenConfiguration.ScreensWithoutBackButton.screens.contains(
                                currentRoute
                            ) && currentRoute != NavigationRoutes.OfferScreen.route
                        ) 0.dp else 30.dp
                    ),
                )
                Text(
                    when (currentRoute) {
                        NavigationRoutes.MessageDetailScreen.route -> currentMessage?.title ?: ""
                        NavigationRoutes.ProfileScreen.route -> "Profile"
                        NavigationRoutes.PaymentSummaryScreen.route -> "Payment Summary"
                        NavigationRoutes.MessageDetailScreen.route -> currentMessage?.title ?: ""
                        else -> ""
                    }.let {
                        if (it.count() > 25) it.take(25) + "..." else it
                    },
                    Modifier.padding(bottom = 17.dp),
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )


            }
            if (ScreenConfiguration.ScreensWithoutBackButton.screens.contains(currentRoute)
                    .not()) {
                Divider(color = Color.Gray.copy(0.25f))
            }
        }
    }
}