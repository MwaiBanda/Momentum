package com.mwaibanda.momentum.android.presentation.messages

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mwaibanda.momentum.domain.controller.MessageController
import com.mwaibanda.momentum.domain.models.Message
import com.mwaibanda.momentum.utils.Result

class MessageViewModel(
    private val messageController: MessageController
): ViewModel() {
    fun getMessages(userId: String, onCompletion: (List<Message>) -> Unit) {
        messageController.getAllMessages(userId) {
            when (it) {
                is Result.Failure -> {
                    Log.e("MessageViewModel[getMessages]", it.message ?: "" )
                }
                is Result.Success -> {
                    onCompletion(it.data ?: emptyList())
                }
            }
        }
    }
}