package com.mwaibanda.momentum.android.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mwaibanda.momentum.android.core.utils.C
import com.mwaibanda.momentum.android.core.utils.Modal
import com.mwaibanda.momentum.android.core.utils.ScreenConfiguration
import com.mwaibanda.momentum.android.presentation.auth.AuthViewModel
import com.mwaibanda.momentum.android.presentation.message.MessageViewModel
import com.mwaibanda.momentum.android.presentation.message.NoteState
import com.mwaibanda.momentum.android.presentation.navigation.BottomBar
import com.mwaibanda.momentum.android.presentation.navigation.TopBar
import com.mwaibanda.momentum.domain.models.Note
import com.mwaibanda.momentum.domain.models.NoteRequest
import com.mwaibanda.momentum.domain.models.Passage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun MomentumEntry(
    isShowingModal: Boolean,
    authViewModel: AuthViewModel,
    messageViewModel: MessageViewModel,
    onShowModal: (Modal) -> Unit,
    content: @Composable (PaddingValues, NavHostController) -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showAddNote by messageViewModel.showAddNote.collectAsState()
    val currentPassage: Passage? by messageViewModel.passage.collectAsState()
    var noteState by rememberSaveable {
        mutableStateOf(NoteState.Display)
    }
    var notes by remember { mutableStateOf("") }
    val currentNote: Note? by messageViewModel.note.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val isUpdatingNote by messageViewModel.isUpdatingNote.collectAsState()
    val passages by messageViewModel.passages.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(currentNote) {
        notes = currentNote?.content ?: ""
    }
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = if (ScreenConfiguration.ScreensWithWhiteStatusBar.screens.contains(currentRoute)) Color.White else Color.Transparent,
            darkIcons = useDarkIcons
        )
    }


    Scaffold {
        Box {
            Box(modifier = Modifier.zIndex(if (isShowingModal) 1f else 0f)) {
                content(it, navController)
            }

            AnimatedVisibility(visible = ScreenConfiguration.ScreensWithoutNavigation.screens.contains(currentRoute).not(), enter = fadeIn(), exit = fadeOut()) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .zIndex(if (isShowingModal) 0f else 1f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    TopBar(
                        navController = navController,
                        currentRoute = currentRoute,
                        messageViewModel = messageViewModel,
                        onShowModal = {
                            onShowModal(Modal.ViewTransactions)
                        }
                    )

                    BottomBar(navController = navController, currentRoute = currentRoute)
                }
            }

            AnimatedVisibility(
                visible = showAddNote,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .zIndex(2f)
                        .background(Color.Black.copy(0.5f))
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            enabled = false
                        ) {},
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        Modifier
                            .clip(RoundedCornerShape(5))
                            .background(Color.White)
                            .padding(20.dp)
                            .fillMaxWidth(0.85f)
                    ) {
                        when (noteState) {
                            NoteState.Display -> Column(Modifier.fillMaxHeight(0.25f)) {
                                Text(text = "Enter your notes", fontWeight = FontWeight.Bold)
                                BasicTextField(
                                    value = notes,
                                    onValueChange = { notes = it },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .padding(vertical = 10.dp)
                                        .border(
                                            width = 1.dp,
                                            color = Color.Gray,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(horizontal = 5.dp)
                                )
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Button(
                                        modifier = Modifier
                                            .weight(0.4f)
                                            .padding(end = 5.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color(C.MOMENTUM_ORANGE),
                                            contentColor = Color.White
                                        ),
                                        onClick = {
                                            messageViewModel.setShowAddNote(false)
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
                                                        messageViewModel.setPassages(passages.map {
                                                            if (it.id == currentPassage?.id) it.copy(
                                                                notes = it.notes?.map { note ->
                                                                    if (note.id == currentNote?.id) note.copy(
                                                                        content = notes
                                                                    ) else note
                                                                }) else it
                                                        })
                                                        messageViewModel.setShowAddNote(false)
                                                        messageViewModel.setIsUpdatingNote(false)
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
                                                            userId = authViewModel.currentUser?.id
                                                                ?: "",
                                                            passageId = currentPassage?.id ?: ""
                                                        )
                                                    ) {
                                                        messageViewModel.setPassages(passages.map {
                                                            if (it.id == currentPassage?.id) it.copy(
                                                                notes = buildList {
                                                                    addAll(it.notes ?: emptyList())
                                                                    add(
                                                                        Note(
                                                                            id = UUID.randomUUID()
                                                                                .toString(),
                                                                            content = notes
                                                                        )
                                                                    )
                                                                }) else it
                                                        })
                                                        messageViewModel.setShowAddNote(false)
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
    }
}
