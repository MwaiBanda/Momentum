package com.mwaibanda.momentum.android.presentation.profie

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mwaibanda.momentum.android.core.utils.Constants
import com.mwaibanda.momentum.android.presentation.components.BasePlainExpandableCard
import com.mwaibanda.momentum.android.presentation.profie.ProfileViewModel.ProfileCard.*
import com.mwaibanda.momentum.utils.MultiplatformConstants
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = getViewModel()) {

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Column {
            Column {
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = MultiplatformConstants.PROFILE,
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(10.dp)
                )
                Divider()
                Text(
                    text = MultiplatformConstants.PROFILE_SUBHEADING.uppercase(),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    color = Color(Constants.MomentumOrange)
                )
            }
            Row(
                Modifier
                    .padding(10.dp)
                    .padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .clip(CircleShape)
                        .size(70.dp)
                        .border(
                            5.dp,
                            Color(Constants.MomentumOrange),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        Modifier
                            .clip(CircleShape)
                            .size(50.dp)
                            .background(Color(Constants.MomentumOrange))
                            .padding(5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Mwai Banda"
                                .split(" ")
                                .map { it.first().toString() }
                                .reduce { x, y -> x + y },
                            fontWeight = FontWeight.ExtraBold,
                            style = MaterialTheme.typography.h6,
                            color = Color.White
                        )
                    }

                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Mwai Banda",
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.h6,
                    )
                    Text(
                        text = "07/29/2019",
                        style = MaterialTheme.typography.caption,
                        color = Color.Gray
                    )
                }
            }

            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                BasePlainExpandableCard(
                    isExpanded = profileViewModel.isContactExpanded,
                    contentHeight = 200,
                    showCoverDivider = true,
                    coverContent = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Expand Icon",
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = MultiplatformConstants.CONTACT_INFORMATION,
                            fontWeight = FontWeight.ExtraBold
                        )
                    },
                    onCoverClick = {
                        profileViewModel.cardToggle(CONTACT_INFO)
                        profileViewModel.closeCards(
                            BILLING_INFO,
                            MANAGE_ACC,
                            TECH_SUPPORT,
                            FEEDBACK,
                            INFORMATION
                        )
                    },
                    coverIcon = { isExpanded ->

                        Icon(
                            imageVector = if (isExpanded)
                                Icons.Default.ArrowDropUp
                            else
                                Icons.Default.ArrowDropDown,
                            contentDescription = "Expand Icon",
                            tint = Color.Gray
                        )

                    }
                ) {
                    Text(text = "Contact Content")
                }

                BasePlainExpandableCard(
                    isExpanded = profileViewModel.isBillingExpanded,
                    contentHeight = 300,
                    showCoverDivider = profileViewModel.isContactExpanded,
                    coverContent = {
                        Icon(
                            imageVector = Icons.Default.Domain, contentDescription = "Expand Icon",
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = MultiplatformConstants.BILLING_INFORMATION,
                            fontWeight = FontWeight.ExtraBold
                        )
                    },
                    onCoverClick = {
                        profileViewModel.cardToggle(BILLING_INFO)
                        profileViewModel.closeCards(
                            CONTACT_INFO,
                            MANAGE_ACC,
                            TECH_SUPPORT,
                            FEEDBACK,
                            INFORMATION
                        )

                    },
                    coverIcon = { isExpanded ->
                        Icon(
                            imageVector = if (isExpanded)
                                Icons.Default.ArrowDropUp
                            else
                                Icons.Default.ArrowDropDown,
                            contentDescription = "Expand Icon",
                            tint = Color.Gray
                        )

                    }
                ) {
                    Text(text = "Billing Content")
                }
                BasePlainExpandableCard(
                    isExpanded = profileViewModel.isManageAccExpanded,
                    contentHeight = 300,
                    showCoverDivider = profileViewModel.isBillingExpanded,
                    coverContent = {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Expand Icon",
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = MultiplatformConstants.MANAGE_ACCOUNT,
                            fontWeight = FontWeight.ExtraBold
                        )
                    },
                    onCoverClick = {
                        profileViewModel.cardToggle(MANAGE_ACC)
                        profileViewModel.closeCards(
                            CONTACT_INFO,
                            BILLING_INFO,
                            TECH_SUPPORT,
                            FEEDBACK,
                            INFORMATION
                        )

                    },
                    coverIcon = { isExpanded ->
                        Icon(
                            imageVector = if (isExpanded)
                                Icons.Default.ArrowDropUp
                            else
                                Icons.Default.ArrowDropDown,
                            contentDescription = "Expand Icon",
                            tint = Color.Gray
                        )

                    }
                ) {
                    Text(text = "Account Content")
                }
                BasePlainExpandableCard(
                    isExpanded = profileViewModel.isTechSupportExpanded,
                    contentHeight = 300,
                    showCoverDivider = profileViewModel.isManageAccExpanded,
                    coverContent = {
                        Icon(
                            imageVector = Icons.Default.SupervisorAccount,
                            contentDescription = "Expand Icon",
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = MultiplatformConstants.TECHNICAL_SUPPORT,
                            fontWeight = FontWeight.ExtraBold
                        )
                    },
                    onCoverClick = {
                        profileViewModel.cardToggle(TECH_SUPPORT)
                        profileViewModel.closeCards(
                            CONTACT_INFO,
                            MANAGE_ACC,
                            BILLING_INFO,
                            FEEDBACK,
                            INFORMATION
                        )

                    },
                    coverIcon = { isExpanded ->
                        Icon(
                            imageVector = if (isExpanded)
                                Icons.Default.ArrowDropUp
                            else
                                Icons.Default.ArrowDropDown,
                            contentDescription = "Expand Icon",
                            tint = Color.Gray
                        )

                    }
                ) {
                    Text(text = "Support Content")
                }
                BasePlainExpandableCard(
                    isExpanded = profileViewModel.isFeedbackExpanded,
                    contentHeight = 300,
                    showCoverDivider = profileViewModel.isTechSupportExpanded,
                    coverContent = {
                        Icon(
                            imageVector = Icons.Outlined.Chat, contentDescription = "Expand Icon",
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = MultiplatformConstants.FEEDBACK,
                            fontWeight = FontWeight.ExtraBold
                        )
                    },
                    onCoverClick = {
                        profileViewModel.cardToggle(FEEDBACK)
                        profileViewModel.closeCards(
                            CONTACT_INFO,
                            MANAGE_ACC,
                            TECH_SUPPORT,
                            BILLING_INFO,
                            INFORMATION
                        )

                    },
                    coverIcon = { isExpanded ->
                        Icon(
                            imageVector = if (isExpanded)
                                Icons.Default.ArrowDropUp
                            else
                                Icons.Default.ArrowDropDown,
                            contentDescription = "Expand Icon",
                            tint = Color.Gray
                        )
                    }
                ) {
                    Text(text = "Feedback Content")
                }
                BasePlainExpandableCard(
                    isExpanded = profileViewModel.isInformationExpanded,
                    contentHeight = 300,
                    showCoverDivider = profileViewModel.isFeedbackExpanded,
                    coverContent = {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Expand Icon",
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = MultiplatformConstants.INFORMATION,
                            fontWeight = FontWeight.ExtraBold
                        )
                    },
                    onCoverClick = {
                        profileViewModel.cardToggle(INFORMATION)
                        profileViewModel.closeCards(
                            CONTACT_INFO,
                            MANAGE_ACC,
                            TECH_SUPPORT,
                            FEEDBACK,
                            BILLING_INFO
                        )

                    },
                    coverIcon = { isExpanded ->
                        Icon(
                            imageVector = if (isExpanded)
                                Icons.Default.ArrowDropUp
                            else
                                Icons.Default.ArrowDropDown,
                            contentDescription = "Expand Icon",
                            tint = Color.Gray
                        )

                    }
                ) {
                    Text(text = "Information Content")
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(ProfileViewModel())
}