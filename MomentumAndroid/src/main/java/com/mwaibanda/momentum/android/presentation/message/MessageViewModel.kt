package com.mwaibanda.momentum.android.presentation.message

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mwaibanda.momentum.domain.controller.MessageController
import com.mwaibanda.momentum.domain.models.Message
import com.mwaibanda.momentum.utils.Result

class MessageViewModel(
    private val messageController: MessageController
): ViewModel() {
    fun getMessages(userId: String, isRefreshing: Boolean = false, onCompletion: (List<Message>) -> Unit) {

        if (isRefreshing) messageController.clearMessagesCache()

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