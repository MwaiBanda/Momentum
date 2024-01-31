package com.mwaibanda.momentum.android.presentation.event

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mwaibanda.momentum.domain.controller.EventController
import com.mwaibanda.momentum.domain.models.EventGroup
import com.mwaibanda.momentum.utils.DataResponse

class EventViewModel(
    private val eventController: EventController
): ViewModel() {
    fun getEvents(onCompletion: (List<EventGroup>) -> Unit) {
        eventController.getAllEvents {
            when (it) {
                is DataResponse.Failure -> {
                    Log.e("MealViewModel[getAllMeals]", it.message ?: "" )
                }
                is DataResponse.Success -> {
                    onCompletion(it.data ?: emptyList())
                }
            }
        }
    }
}