package com.mwaibanda.momentum.android.presentation.event

import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mwaibanda.momentum.android.presentation.message.MessageViewModel
import com.mwaibanda.momentum.domain.controller.EventController
import com.mwaibanda.momentum.domain.models.EventGroup
import com.mwaibanda.momentum.utils.DataResponse
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive

class EventViewModel(
    private val eventController: EventController
): ViewModel() {
    private val _events = MutableStateFlow(emptyList<EventGroup>())
    private val eventGroups = _events.asStateFlow()

    private val _searchTerm = MutableStateFlow("")
    val searchTerm = _searchTerm.asStateFlow()

    val filteredEvents = combine(
        eventGroups,
        searchTerm
    ) { groups, term ->
        groups.filter {
            it.containsTerm(term)
        }.map { group ->  group.copy(
            events = group.events.filter { event ->
                event.containsTerm(term)
            }
        ) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    fun onSearchTermChanged(value: String) {
        _searchTerm.value = value
    }
    fun getEvents() {
        eventController.getAllEvents {
            when (it) {
                is DataResponse.Failure -> {
                    Log.e("MealViewModel[getAllMeals]", it.message ?: "" )
                }
                is DataResponse.Success -> {
                    _events.value = it.data ?: emptyList()
                }
            }
        }
    }

    fun searchTag(): Flow<String> = flow {
        while (currentCoroutineContext().isActive) {
            delay(2500)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                searchTags.add(MessageViewModel.searchTags.removeFirst())
            } else {
                searchTags = buildList {
                    addAll(MessageViewModel.searchTags)
                    add(removeAt(0))
                }.toMutableList()
            }
            emit(searchTags.first())
        }
    }
    companion object {
        var searchTags = mutableListOf(
            "by event",
            "by date",
        )
    }
}