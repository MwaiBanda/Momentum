package com.mwaibanda.momentum.android.presentation.message

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mwaibanda.momentum.domain.controller.MessageController
import com.mwaibanda.momentum.domain.models.Message
import com.mwaibanda.momentum.domain.models.Note
import com.mwaibanda.momentum.domain.models.NoteRequest
import com.mwaibanda.momentum.domain.usecase.message.PostNoteUseCase
import com.mwaibanda.momentum.domain.usecase.message.UpdateNoteUseCase
import com.mwaibanda.momentum.utils.DataResponse
import com.mwaibanda.momentum.utils.Result
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MessageViewModel(
    private val messageController: MessageController,
    private val postNoteUseCase: PostNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
): ViewModel() {
    fun getMessages(userId: String, isRefreshing: Boolean = false, onCompletion: (List<Message>) -> Unit) {

        if (isRefreshing) messageController.clearMessagesCache()

        messageController.getAllMessages(userId) {
            when (it) {
                is DataResponse.Failure -> {
                    Log.e("MessageViewModel[getMessages]", it.message ?: "" )
                }
                is DataResponse.Success -> {
                    onCompletion(it.data ?: emptyList())
                }
            }
        }
    }

    fun clearMessagesCache() {
        messageController.clearMessagesCache()
    }
    fun postNote(request: NoteRequest, onCompletion: () -> Unit) {
        viewModelScope.launch {
            postNoteUseCase(request).collectLatest {
                when(it) {
                    is Result.Data -> {
                        Log.e("POST[Data]", "NOTE")
                        onCompletion()
                    }
                    is Result.Error -> Log.e("POST[Error]", "NOTE")
                    is Result.Loading -> Log.e("POST[Loading]", "NOTE")
                }
            }
        }
    }

    fun updateNote(note: Note.UserNote, onCompletion: () -> Unit) {
        viewModelScope.launch {
            updateNoteUseCase(note).collectLatest {
                when(it) {
                    is Result.Data -> {
                        Log.e("PUT[Data]", "NOTE")
                        onCompletion()
                    }
                    is Result.Error -> Log.e("PUT[Error]", "NOTE")
                    is Result.Loading -> Log.e("PUT[Loading]", "NOTE")
                }
            }
        }
    }
}