package com.mwaibanda.momentum.android.presentation.event

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mwaibanda.momentum.domain.controller.EventController
import com.mwaibanda.momentum.domain.models.GroupedEvent
import com.mwaibanda.momentum.utils.Result

class EventViewModel(
    private val eventController: EventController
): ViewModel() {
    fun getEvents(onCompletion: (List<GroupedEvent>) -> Unit) {
        eventController.getAllEvents {
            when (it) {
                is Result.Failure -> {
                    Log.e("MealViewModel[getAllMeals]", it.message ?: "" )
                }
                is Result.Success -> {
                    onCompletion(it.data ?: emptyList())
                }
            }
        }
    }
}