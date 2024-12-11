package com.mwaibanda.momentum.android.presentation.message

import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mwaibanda.momentum.android.presentation.sermon.SermonViewModel
import com.mwaibanda.momentum.domain.models.Message
import com.mwaibanda.momentum.domain.models.MessageGroup
import com.mwaibanda.momentum.domain.models.Note
import com.mwaibanda.momentum.domain.models.NoteRequest
import com.mwaibanda.momentum.domain.models.Passage
import com.mwaibanda.momentum.domain.usecase.message.MessageUseCases
import com.mwaibanda.momentum.utils.MultiplatformConstants
import com.mwaibanda.momentum.utils.Result
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MessageViewModel(
    private val messageUseCases: MessageUseCases
): ViewModel() {
    private val _messages = MutableStateFlow(emptyList<MessageGroup>())
    private val messageGroups = _messages.asStateFlow()

    private val _message: MutableStateFlow<Message?> = MutableStateFlow(null)
    val message = _message.asStateFlow()

    private val _passages: MutableStateFlow<List<Passage>> = MutableStateFlow(emptyList())
    val passages = _passages.asStateFlow()

    private val _passage: MutableStateFlow<Passage?> = MutableStateFlow(null)
    val passage = _passage.asStateFlow()

    private val _note: MutableStateFlow<Note?> = MutableStateFlow(null)
    val note = _note.asStateFlow()

    private val _series = MutableStateFlow(emptyList<String>())
    val series = _series.asStateFlow()

    private val _searchTerm = MutableStateFlow("")
    val searchTerm = _searchTerm.asStateFlow()

    private val _showAddNote = MutableStateFlow(false)
    val showAddNote = _showAddNote.asStateFlow()

    private val _isUpdatingNote = MutableStateFlow(false)
    val isUpdatingNote = _isUpdatingNote.asStateFlow()

    private val _filterBySeries = MutableStateFlow("")
    val filterBySeries = _filterBySeries.asStateFlow()

    val filteredMessages = combine(
        messageGroups,
        searchTerm,
        filterBySeries,
    ) { groups, term, series ->
        groups.filter {
            it.containsTerm(term)
        }.map { group ->  group.copy(
            messages = group.messages.filter { message ->
                message.containsTerm(term)
            }
        ) }.filter {
            it.series.lowercase().contains(series.lowercase()) || series.isEmpty()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun getMessages(userId: String, isRefreshing: Boolean = false, onCompletion: () -> Unit = {}) {
        viewModelScope.launch {
            if (isRefreshing) clearMessagesCache()

            messageUseCases.read(userId).collectLatest {
                when(it) {
                    is Result.Data -> {
                        _messages.value = (it.data ?: emptyList())
                        _series.value = _messages.value.map { it.series }
                        Log.e("GET[Data]", "MESSAGE")
                        onCompletion()
                    }
                    is Result.Error -> Log.e("MessageViewModel[getMessages]", it.message ?: "")

                    is Result.Loading -> Log.e("GET[Loading]", "MESSAGE")
                }
            }

        }
    }

    fun setMessage(value: Message) {
        _message.value = value
    }

    fun setNote(value: Note) {
        _note.value = value
    }

    fun setPassage(value: Passage) {
        _passage.value = value
    }

    fun setPassages(value: List<Passage>) {
        _passages.value = value
    }


    fun setShowAddNote(value: Boolean) {
        _showAddNote.value = value
    }

    fun setIsUpdatingNote(value: Boolean) {
        _isUpdatingNote.value = value
    }

    fun clearMessagesCache() {
        messageUseCases.clearCache(MultiplatformConstants.MESSAGE_KEY)
    }
    fun postNote(request: NoteRequest, onCompletion: () -> Unit) {
        viewModelScope.launch {
            messageUseCases.create(request).collectLatest {
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
            messageUseCases.update(note).collectLatest {
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


    fun onSearchTermChanged(value: String) {
        _searchTerm.value = value
    }
    fun onFilteredSeriesChanged(value: String) {
        _filterBySeries.value = value
    }

    fun searchTag(): Flow<String> = flow {
        while (currentCoroutineContext().isActive) {
            delay(2500)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                searchTags.add(searchTags.removeFirst())
            } else {
                searchTags = buildList {
                    addAll(searchTags)
                    add(removeAt(0))
                }.toMutableList()
            }
            emit(SermonViewModel.searchTags.first())
        }
    }
    companion object {
        var searchTags = mutableListOf(
            "by message",
            "for preachers",
            "by series",
            "by date"
        )
    }
}