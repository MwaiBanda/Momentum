package com.mwaibanda.momentum.android.presentation.sermon

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mwaibanda.momentum.data.db.MomentumSermon
import com.mwaibanda.momentum.domain.controller.SermonController
import com.mwaibanda.momentum.domain.models.Sermon
import com.mwaibanda.momentum.utils.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class SermonViewModel(
    private val sermonController: SermonController,
): ViewModel() {
    private var currentPage by mutableStateOf(1)
    private val sermons = MutableStateFlow(emptyList<Sermon>())
    private val _searchTerm = MutableStateFlow("")
    val searchTerm = _searchTerm.asStateFlow()

    var filteredSermons = combine(sermons, searchTerm) { sermons, searchTerm ->
        sermons
            .filter {
                 it.title.contains(searchTerm) ||
                 it.preacher.contains(searchTerm) ||
                 it.series.contains(searchTerm) ||
                 searchTerm.isEmpty()
            }
            .sortedBy { it.date }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val  _canLoadMoreSermons = MutableStateFlow(true)
    val canLoadMoreSermons = _canLoadMoreSermons.asStateFlow()

    var currentSermon by mutableStateOf<Sermon?>(null)
    var watchedSermons by mutableStateOf<List<MomentumSermon>>(emptyList())

    fun  onSearchTermChanged(searchTerm: String) {
        viewModelScope.launch {
            launch {
                _searchTerm.value = searchTerm
            }
            delay(2000)
            if (_canLoadMoreSermons.value){
                loadMoreSermons()
            }
        }
    }
    fun fetchSermons() {
        currentPage = 1
        sermonController.getSermon(currentPage) {
            when(it) {
                is Result.Failure -> Log.d("SERMON/FAILURE", "${it.message}")
                is Result.Success -> {
                    sermons.value = it.data?.sermons ?: emptyList()
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
                    sermons.value += it.data?.sermons ?: emptyList()
                    _canLoadMoreSermons.value = it.data?.canLoadMoreSermons ?: false
                    Log.d("SERMON/SUCCESS", "${it.data}")
                }
            }
        }
    }

    fun addSermon(sermon: MomentumSermon, onCompletion: () -> Unit) {
        sermonController.addSermon(sermon = sermon)
        onCompletion()
    }

    fun getWatchSermons() {
        sermonController.getWatchedSermons { sermons ->
            watchedSermons = sermons
        }
    }
}