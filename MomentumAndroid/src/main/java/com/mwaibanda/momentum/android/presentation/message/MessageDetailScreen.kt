package com.mwaibanda.momentum.android.presentation.message

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mwaibanda.momentum.android.core.utils.Modal
import com.mwaibanda.momentum.android.presentation.auth.AuthViewModel
import com.mwaibanda.momentum.domain.models.Message

enum class NoteState {
    Display,
    Loading
}
@Composable
fun MessageDetailScreen(
    messageViewModel: MessageViewModel,
    authViewModel: AuthViewModel,
    onShowModal: (Modal) -> Unit
) {
    val message: Message? by messageViewModel.message.collectAsState()
    val passages by messageViewModel.passages.collectAsState()

    LaunchedEffect(key1 = message, block = {
        messageViewModel.setPassages(message?.passages ?: emptyList())
        Log.e("MessageDetailScreen", message.toString())
    })



    Box {


            Column(
                Modifier
                    .padding(top = 15.dp)
                    .padding(vertical = 60.dp)
                    .navigationBarsPadding()
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(message?.thumbnail)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Sermon thumbnail",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .heightIn(max = 350.dp)
                        .fillMaxWidth()
                )
                Column(Modifier.padding(horizontal = 10.dp)) {
                    passages.forEach { passage ->
                        Divider(color = Color.Gray.copy(0.25f))
                        Spacer(modifier = Modifier.height(15.dp))
                        if (passage.header.isNullOrEmpty()) {
                            Text(text = passage.verse.toString(), fontWeight = FontWeight.Bold)
                            Text(text = buildAnnotatedString {
                                (passage.message.toString()).forEach {
                                    if (it.isDigit()) {
                                        withStyle(
                                            SpanStyle(
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.Gray
                                            )
                                        ) {
                                            append(it)
                                        }
                                    } else {
                                        append(it)
                                    }
                                }
                            })
                        } else {
                            Text(text = passage.header.toString())
                        }
                        if (passage.notes.isNullOrEmpty()) {
                            ClickableText(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                                        append("ADD NOTES")
                                    }
                                },
                                onClick = {
                                    if (authViewModel.currentUser?.isGuest == true) {
                                        onShowModal(Modal.Authentication)
                                    } else {
                                        messageViewModel.setPassage(passage)
                                        messageViewModel.setShowAddNote(true)
                                    }

                                },
                                modifier = Modifier
                                    .padding(vertical = 10.dp)
                                    .fillMaxWidth()
                            )
                        } else {
                            Text(
                                text = "NOTES",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                            passage.notes?.forEachIndexed { index, note ->
                                ClickableText(
                                    text = AnnotatedString(note.content),
                                    onClick = {
                                        messageViewModel.setNote(note)
                                        messageViewModel.setPassage(passage)
                                        messageViewModel.setIsUpdatingNote(true)
                                        messageViewModel.setShowAddNote(true)
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(top = if (index > 0) 10.dp else 0.dp)
                                )
                            }
                            ClickableText(
                                text = buildAnnotatedString {
                                    withStyle(SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                                        append("ADD MORE NOTES")
                                    }
                                },
                                onClick = {
                                    if (authViewModel.currentUser?.isGuest == true) {
                                        onShowModal(Modal.Authentication)
                                    } else {
                                        messageViewModel.setPassage(passage)
                                        messageViewModel.setShowAddNote(true)
                                    }

                                },
                                modifier = Modifier
                                    .padding(vertical = 10.dp)
                                    .fillMaxWidth()
                            )
                        }
//                Text(text = "Add Notes", fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
            }



    }
}
