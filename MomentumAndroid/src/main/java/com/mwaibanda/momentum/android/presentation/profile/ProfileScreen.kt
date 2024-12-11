package com.mwaibanda.momentum.android.presentation.profile

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.presentation.auth.AuthViewModel
import com.mwaibanda.momentum.android.presentation.components.BasePlainExpandableCard
import com.mwaibanda.momentum.android.presentation.components.BottomSpacing
import com.mwaibanda.momentum.android.presentation.components.LinkLabel
import com.mwaibanda.momentum.android.presentation.components.TitleTextField
import com.mwaibanda.momentum.android.presentation.profile.ProfileViewModel.ProfileCard.BILLING_INFO
import com.mwaibanda.momentum.android.presentation.profile.ProfileViewModel.ProfileCard.CONTACT_INFO
import com.mwaibanda.momentum.android.presentation.profile.ProfileViewModel.ProfileCard.FEEDBACK
import com.mwaibanda.momentum.android.presentation.profile.ProfileViewModel.ProfileCard.INFORMATION
import com.mwaibanda.momentum.android.presentation.profile.ProfileViewModel.ProfileCard.MANAGE_ACC
import com.mwaibanda.momentum.android.presentation.profile.ProfileViewModel.ProfileCard.TECH_SUPPORT
import com.mwaibanda.momentum.domain.models.User
import com.mwaibanda.momentum.utils.MultiplatformConstants
import com.mwaibanda.momentum.utils.getFormattedDate

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel,
    authViewModel: AuthViewModel
) {
    LaunchedEffect(key1 = Unit) {
        if (authViewModel.currentUser?.isGuest == false) {
            profileViewModel.getContactInformation(userId = authViewModel.currentUser?.id ?: "") {
                profileViewModel.getBillingInformation(userId = authViewModel.currentUser?.id ?: "")
            }
        }
    }
    BackHandler {
       navController.popBackStack()
    }
    DisposableEffect(key1 = Unit) {
        onDispose {
            if (authViewModel.currentUser?.isGuest == false) {
                profileViewModel.updateUser(User(
                    fullname = profileViewModel.fullname,
                    email = profileViewModel.email,
                    phone = profileViewModel.phone,
                    userId = authViewModel.currentUser?.id ?: "",
                ))
            }
        }
    }
    val context = LocalContext.current
    val mailTo: (email: String) -> Unit = { email ->
        val intent = Intent().apply {
            action = Intent.ACTION_SENDTO
            data = Uri.parse("mailto:$email")
        }
        context.startActivity(intent)
    }
    val phone: (phone: String) -> Unit = { phone ->
        val intent = Intent().apply {
            action = Intent.ACTION_DIAL
            data = Uri.parse("tel:$phone")
        }
        context.startActivity(intent)
    }

    Box {
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 5.dp)
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Column {
                Column {
                    Spacer(modifier = Modifier.height(90.dp))
                    Text(
                        text = MultiplatformConstants.PROFILE_SUBHEADING.uppercase(),
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(horizontal = 10.dp),
                        color = Color(C.MOMENTUM_ORANGE)
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
                                Color(C.MOMENTUM_ORANGE),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            Modifier
                                .clip(CircleShape)
                                .size(50.dp)
                                .background(Color(C.MOMENTUM_ORANGE))
                                .padding(5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = profileViewModel.fullname.ifEmpty { "Guest" }
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
                            text = profileViewModel.fullname.ifEmpty { "Guest" },
                            fontWeight = FontWeight.ExtraBold,
                            style = MaterialTheme.typography.h6,
                        )
                        Text(
                            text = "Est. ${
                                if (profileViewModel.createdOn.isEmpty())
                                    "Create an Account or Sign In" 
                                else if (profileViewModel.createdOn.contains("Z")) 
                                    getFormattedDate(
                                        profileViewModel.createdOn, 
                                        "mm/dd/yyyy"
                                    ) 
                                else profileViewModel.createdOn
                            }",
                            style = MaterialTheme.typography.caption,
                            color = Color.Gray
                        )
                    }
                }

                Column(

                ) {
                    /**
                     * @Contact_Information
                     */
                    BasePlainExpandableCard(
                        isExpanded = profileViewModel.isContactExpanded,
                        contentHeight = 315,
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
                                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.ExtraBold)
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
                        TitleTextField(
                            title = MultiplatformConstants.FULLNAME,
                            text = profileViewModel.fullname,
                            onTextChange = { profileViewModel.fullname = it }
                        ) {
                            profileViewModel.updateFullname(userId = authViewModel.currentUser?.id
                                ?: "")
                        }
                        Divider()
                        TitleTextField(
                            title = MultiplatformConstants.PHONE,
                            text = profileViewModel.phone,
                            onTextChange = { profileViewModel.phone = it }
                        ) {
                            profileViewModel.updatePhone(userId = authViewModel.currentUser?.id
                                ?: "")
                        }
                        Divider()
                        TitleTextField(
                            title = MultiplatformConstants.EMAIL,
                            text = profileViewModel.email,
                            onTextChange = { profileViewModel.email = it }
                        ) {
                            profileViewModel.updateEmail(userId = authViewModel.currentUser?.id
                                ?: "")
                        }
                        Divider()
                        TitleTextField(
                            title = MultiplatformConstants.PASSWORD,
                            text = profileViewModel.password,
                            onTextChange = { profileViewModel.password = it }
                        ) {
                            profileViewModel.updatePassword(userId = authViewModel.currentUser?.id
                                ?: "")
                        }
                    }
                    /**
                     * @Billing_Information
                     */
                    BasePlainExpandableCard(
                        isExpanded = profileViewModel.isBillingExpanded,
                        contentHeight = 315,
                        showCoverDivider = profileViewModel.isContactExpanded,
                        coverContent = {
                            Icon(
                                imageVector = Icons.Default.Domain,
                                contentDescription = "Expand Icon",
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = MultiplatformConstants.BILLING_INFORMATION,
                                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.ExtraBold)
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
                        TitleTextField(
                            title = MultiplatformConstants.STREET_ADDRESS,
                            text = profileViewModel.streetAddress,
                            onTextChange = { profileViewModel.streetAddress = it }
                        ) {
                            profileViewModel.updateStreetAddress(userId = authViewModel.currentUser?.id
                                ?: "")
                        }
                        Divider()
                        TitleTextField(
                            title = MultiplatformConstants.APT,
                            text = profileViewModel.apt,
                            onTextChange = { profileViewModel.apt = it }
                        ) {
                            profileViewModel.updateApt(userId = authViewModel.currentUser?.id ?: "")
                        }
                        Divider()
                        TitleTextField(
                            title = MultiplatformConstants.CITY,
                            text = profileViewModel.city,
                            onTextChange = { profileViewModel.city = it }
                        ) {
                            profileViewModel.updateCity(userId = authViewModel.currentUser?.id
                                ?: "")
                        }
                        Divider()
                        TitleTextField(
                            title = MultiplatformConstants.ZIP_CODE,
                            text = profileViewModel.zipCode,
                            onTextChange = { profileViewModel.zipCode = it }
                        ) {
                            profileViewModel.updateZipCode(userId = authViewModel.currentUser?.id
                                ?: "")
                        }
                    }
                    /**
                     * @Manage_Account
                     */
                    BasePlainExpandableCard(
                        isExpanded = profileViewModel.isManageAccExpanded,
                        contentHeight = 230,
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
                                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.ExtraBold)
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
                        Column(
                            Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = MultiplatformConstants.DELETE_ACCOUNT,
                                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.ExtraBold),
                                textAlign = TextAlign.Start,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(text = MultiplatformConstants.DELETION_WARNING, color = Color.Gray)
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedButton(
                                onClick = {
                                    profileViewModel.deleteUser(userId = authViewModel.currentUser?.id
                                        ?: "") {
                                        authViewModel.deleteCurrentUser {
                                            authViewModel.signInAsGuest()
                                            navController.popBackStack()
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(55.dp),
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(2.dp, Color(C.MOMENTUM_ORANGE))
                            ) {
                                Text(
                                    text = MultiplatformConstants.DELETE_ACCOUNT,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(C.MOMENTUM_ORANGE)
                                )
                            }
                        }
                    }
                    /**
                     * @Technical_Support
                     */
                    BasePlainExpandableCard(
                        isExpanded = profileViewModel.isTechSupportExpanded,
                        contentHeight = 260,
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
                                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.ExtraBold)
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
                        Column(
                            Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = MultiplatformConstants.TECHNICAL_SUPPORT_PROMPT,
                                color = Color.Gray,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            LinkLabel(
                                title = MultiplatformConstants.TECHNICAL_SUPPORT,
                                description = MultiplatformConstants.DEVELOPER_PHONE_TITLE
                            ) {
                                phone(MultiplatformConstants.DEVELOPER_PHONE)
                            }
                            LinkLabel(
                                title = MultiplatformConstants.TECHNICAL_SUPPORT,
                                description = MultiplatformConstants.DEVELOPER_EMAIL_TITLE
                            ) {
                                mailTo(MultiplatformConstants.DEVELOPER_EMAIL)
                            }
                        }
                    }
                    /**
                     * @Feedback
                     */
                    BasePlainExpandableCard(
                        isExpanded = profileViewModel.isFeedbackExpanded,
                        contentHeight = 190,
                        showCoverDivider = profileViewModel.isTechSupportExpanded,
                        coverContent = {
                            Icon(
                                imageVector = Icons.Outlined.Chat,
                                contentDescription = "Expand Icon",
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = MultiplatformConstants.FEEDBACK,
                                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.ExtraBold)
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
                        Column(
                            Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = MultiplatformConstants.FEEDBACK_PROMPT,
                                color = Color.Gray,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            LinkLabel(
                                title = MultiplatformConstants.FEEDBACK,
                                description = MultiplatformConstants.DEVELOPER
                            ) {
                                mailTo(MultiplatformConstants.DEVELOPER_EMAIL)
                            }
                        }
                    }
                    /**
                     * @Information
                     */
                    BasePlainExpandableCard(
                        isExpanded = profileViewModel.isInformationExpanded,
                        contentHeight = 445,
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
                                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.ExtraBold)
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
                        Column(
                            Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = MultiplatformConstants.ABOUT_CHURCH,
                                color = Color.Gray,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            LinkLabel(
                                title = MultiplatformConstants.MOMENTUM_PHONE,
                                description = MultiplatformConstants.CHURCH_PHONE_TITLE
                            ) {
                                phone(MultiplatformConstants.CHURCH_PHONE)
                            }
                            LinkLabel(
                                title = MultiplatformConstants.MOMENTUM_PHONE,
                                description = MultiplatformConstants.CHURCH_EMERGENCY_PHONE_TITLE
                            ) {
                                phone(MultiplatformConstants.CHURCH_EMERGENCY_PHONE)
                            }
                            LinkLabel(
                                title = MultiplatformConstants.MOMENTUM_EMAIL,
                                description = MultiplatformConstants.CHURCH_EMAIL_TITLE
                            ) {
                                mailTo(MultiplatformConstants.CHURCH_EMAIL)
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = MultiplatformConstants.COPYRIGHT,
                                color = Color.Gray,
                                style = MaterialTheme.typography.caption
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                        Divider()
                    }
                }
            }
            Spacer(modifier = Modifier.height(70.dp))
            BottomSpacing()
        }
        Column(
            Modifier
                .fillMaxSize()
                .offset(y = 4.dp), verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        authViewModel.signOut {
                            authViewModel.signInAsGuest()
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(55.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE55F1F))
                ) {
                    Text(
                        text = "Sign Out",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Divider()
            }
            BottomSpacing()
        }
    }

}

