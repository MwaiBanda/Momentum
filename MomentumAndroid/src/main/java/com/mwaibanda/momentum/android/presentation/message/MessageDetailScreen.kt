package com.mwaibanda.momentum.android.presentation.message

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.core.utils.Modal
import com.mwaibanda.momentum.android.presentation.auth.AuthViewModel
import com.mwaibanda.momentum.domain.models.Message
import com.mwaibanda.momentum.domain.models.Note
import com.mwaibanda.momentum.domain.models.NoteRequest
import com.mwaibanda.momentum.domain.models.Passage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

enum class NoteState {
    Display,
    Loading
}
@Composable
fun MessageDetailScreen(
    message: Message,
    messageViewModel: MessageViewModel,
    authViewModel: AuthViewModel,
    onShowModal: (Modal) -> Unit
) {
    var currentPassage: Passage? by remember {
        mutableStateOf(null)
    }
    var currentNote: Note? by remember {
        mutableStateOf(null)
    }
    var addNote by remember {
        mutableStateOf(false)
    }
    var isUpdatingNote by rememberSaveable {
        mutableStateOf(false)
    }

    var noteState by rememberSaveable {
        mutableStateOf(NoteState.Display)
    }

    var passages by remember {
        mutableStateOf(emptyList<Passage>())
    }
    val coroutineScope = rememberCoroutineScope()
    var notes by remember { mutableStateOf("") }

    LaunchedEffect(key1 = Unit, block = {
        passages = message.passages
        Log.e("MessageDetailScreen", message.toString())
    })

    Box {

        Column(
            Modifier
                .padding(bottom = 60.dp)
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(message.thumbnail)
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
                    Divider()
                    Spacer(modifier = Modifier.height(15.dp))
                    if (passage.header.isNullOrEmpty()) {
                        Text(text = passage.verse.toString(), fontWeight = FontWeight.Bold)
                        Text(text = buildAnnotatedString {
                            (passage.message.toString()).forEach {
                                if (it.isDigit()) {
                                    withStyle(SpanStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)){
                                        append(it)
                                    }
                                } else {
                                    append(it)
                                }
                            }
                        })
                    } else  {
                        Text(text = passage.header.toString())
                    }
                    if (passage.notes.isNullOrEmpty()) {
                        ClickableText(text = buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.ExtraBold)){
                                append("ADD NOTES")
                            }},
                            onClick = {
                                if (authViewModel.currentUser?.isGuest == true) {
                                    onShowModal(Modal.Authentication)
                                } else {
                                    currentPassage = passage
                                    addNote = true
                                }

                            },
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                                .fillMaxWidth()
                        )
                    } else {
                        Text(text = "NOTES", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 10.dp))
                        passage.notes?.forEachIndexed { index, note ->
                            ClickableText(text = AnnotatedString(note.content),
                                onClick = {
                                    currentNote = note
                                    notes = currentNote?.content ?: "No content"
                                    currentPassage = passage
                                    isUpdatingNote = true
                                    addNote = true
                                },
                                modifier = Modifier.fillMaxWidth().padding(top = if (index > 0) 10.dp else 0.dp)
                            )
                        }
                        ClickableText(text = buildAnnotatedString {
                            withStyle(SpanStyle(fontWeight = FontWeight.ExtraBold)){
                                append("ADD MORE NOTES")
                            }},
                            onClick = {
                                if (authViewModel.currentUser?.isGuest == true) {
                                    onShowModal(Modal.Authentication)
                                } else {
                                    currentPassage = passage
                                    addNote = true
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
        AnimatedVisibility(
            visible = addNote,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier
                        .clip(RoundedCornerShape(5))
                        .background(Color.White)
                        .padding(20.dp)
                        .fillMaxWidth(0.85f)
                ) {
                    when(noteState){
                        NoteState.Display -> Column(Modifier.fillMaxHeight(0.25f)) {
                            Text(text = "Enter your notes", fontWeight = FontWeight.Bold)
                            BasicTextField(
                                value = notes,
                                onValueChange = { notes = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp)
                                    .padding(vertical = 10.dp)
                                    .border(
                                        width = 1.dp,
                                        color = Color.Gray,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 5.dp)
                            )
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Button(
                                    modifier = Modifier
                                        .weight(0.4f)
                                        .padding(end = 5.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(C.MOMENTUM_ORANGE),
                                        contentColor = Color.White
                                    ),
                                    onClick = {
                                        addNote = false
                                        notes = ""
                                    }) {
                                    Text(text = "Cancel")
                                }
                                Button(
                                    modifier = Modifier
                                        .weight(0.4f)
                                        .padding(start = 5.dp),
                                    onClick = {
                                        if (authViewModel.currentUser?.isGuest == true) {
                                            onShowModal(Modal.Authentication)
                                        } else {
                                            noteState = NoteState.Loading
                                            if (isUpdatingNote) {
                                                messageViewModel.updateNote(
                                                    Note.UserNote(
                                                        id = currentNote?.id ?: "",
                                                        content = notes,
                                                        userId = authViewModel.currentUser?.id ?: ""
                                                    )
                                                ) {
                                                    passages = passages.map { if (it.id == currentPassage?.id) it.copy(notes = buildList {
                                                        addAll(it.notes ?: emptyList())
                                                        add(Note(id = currentNote?.id ?: "", content = notes))
                                                    }) else it }
                                                    addNote = false
                                                    isUpdatingNote = false
                                                    notes = ""
                                                    messageViewModel.clearMessagesCache()
                                                    coroutineScope.launch {
                                                        delay(1000)
                                                        noteState = NoteState.Display
                                                    }
                                                }
                                            } else {
                                                messageViewModel.postNote(
                                                    NoteRequest(
                                                        content = notes,
                                                        userId = authViewModel.currentUser?.id ?: "",
                                                        passageId = currentPassage?.id ?: ""
                                                    )
                                                ) {
                                                    passages = passages.map { if (it.id == currentPassage?.id) it.copy(notes = buildList {
                                                        addAll(it.notes ?: emptyList())
                                                        add(Note(id = UUID.randomUUID().toString(), content = notes))
                                                    }) else it }
                                                    addNote = false
                                                    notes = ""
                                                    messageViewModel.clearMessagesCache()
                                                    coroutineScope.launch {
                                                        delay(1000)
                                                        noteState = NoteState.Display
                                                    }
                                                }
                                            }
                                        }

                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color(C.MOMENTUM_ORANGE),
                                        contentColor = Color.White
                                    ),
                                ) {
                                    Text(text = "Save")
                                }
                            }
                        }
                        NoteState.Loading -> Column(
                            Modifier
                                .fillMaxHeight(0.25f)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                color = Color(C.MOMENTUM_ORANGE)
                            )
                        }
                    }


                }
            }
        }
    }
