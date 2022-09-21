package com.mwaibanda.momentum.android.presentation.sermon

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mwaibanda.momentum.domain.controller.SermonController
import com.mwaibanda.momentum.domain.models.Sermon
import com.mwaibanda.momentum.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SermonViewModel(
    private val sermonController: SermonController
): ViewModel() {
    private val  _sermons = MutableStateFlow(emptyList<Sermon>())
    val sermon = _sermons.asStateFlow()

    fun fetchSermons() {
        sermonController.getSermon(1) {
            when(it) {
                is Result.Failure -> Log.d("SERMON/FAILURE", "${it.message}")
                is Result.Success -> {
                    _sermons.value = it.data ?: emptyList()
                    Log.d("SERMON/SUCCESS", "${it.data}")
                }
            }
        }
    }
}