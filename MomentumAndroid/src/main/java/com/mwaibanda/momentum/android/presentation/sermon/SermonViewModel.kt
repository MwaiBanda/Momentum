package com.mwaibanda.momentum.android.presentation.sermon

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    private var currentPage by mutableStateOf(1)
    private val  _canLoadMoreSermons = MutableStateFlow(true)
    val canLoadMoreSermons = _canLoadMoreSermons.asStateFlow()

    fun fetchSermons() {
        currentPage = 1
        sermonController.getSermon(currentPage) {
            when(it) {
                is Result.Failure -> Log.d("SERMON/FAILURE", "${it.message}")
                is Result.Success -> {
                    _sermons.value = it.data?.sermons ?: emptyList()
                    _canLoadMoreSermons.value = it.data?.canLoadMoreSermons ?: false
                    Log.d("SERMON/SUCCESS", "${it.data}")
                }
            }
        }
    }

    fun loadMoreSermons() {
        currentPage++
        sermonController.getSermon(currentPage) {
            when(it) {
                is Result.Failure -> {
                    _canLoadMoreSermons.value = false
                    Log.d("SERMON/FAILURE", "${it.message}")
                }
                is Result.Success -> {
                    _sermons.value += it.data?.sermons ?: emptyList()
                    _canLoadMoreSermons.value = it.data?.canLoadMoreSermons ?: false
                    Log.d("SERMON/SUCCESS", "${it.data}")
                }
            }
        }
    }
}