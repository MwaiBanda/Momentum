package com.mwaibanda.momentum.android.presentation.sermon

import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mwaibanda.momentum.android.presentation.message.MessageViewModel
import com.mwaibanda.momentum.data.db.MomentumSermon
import com.mwaibanda.momentum.data.db.SermonFavourite
import com.mwaibanda.momentum.domain.controller.SermonController
import com.mwaibanda.momentum.domain.models.Sermon
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
import kotlinx.coroutines.launch


class SermonViewModel(
    private val sermonController: SermonController,
) : ViewModel() {
    private var currentPage by mutableStateOf(1)
    private val sermons = MutableStateFlow(emptyList<Sermon>())

    private val _searchTerm = MutableStateFlow("")
    val searchTerm = _searchTerm.asStateFlow()

    private val _filterFavourites = MutableStateFlow(false)
    val filterFavourites = _filterFavourites.asStateFlow()

    private val _filterNewest = MutableStateFlow(false)
    val filterNewest = _filterNewest.asStateFlow()

    private val _filterOldest = MutableStateFlow(false)
    val filterOldest = _filterOldest.asStateFlow()

    private val _favouriteSermon = MutableStateFlow(emptyList<SermonFavourite>().toMutableList())
    val favouriteSermons = _favouriteSermon.asStateFlow()

    private val _watchedSermons = MutableStateFlow(emptyList<MomentumSermon>())
    var watchedSermons = _watchedSermons.asStateFlow()

    private val _isLandscape = MutableStateFlow(false)
    var isLandscape = _isLandscape.asStateFlow()


    fun setLandscape(value: Boolean) {
        _isLandscape.value = value
    }

    val filteredSermons = combine(
        sermons,
        searchTerm,
        favouriteSermons,
        filterFavourites,
        filterOldest
    ) { sermons, searchTerm, favouriteSermons, filterFavourites, filterOldest ->
        val filtered = sermons.filter { sermon ->
            sermon.title.lowercase().contains(searchTerm.lowercase()) ||
                    sermon.preacher.lowercase().contains(searchTerm.lowercase()) ||
                    sermon.date.lowercase().contains(searchTerm.lowercase()) ||
                    sermon.series.lowercase().contains(searchTerm.lowercase()) ||
                    searchTerm.isEmpty()
        }.filter { sermon ->
            filterFavourites  && favouriteSermons.firstOrNull { it.id == sermon.id } != null ||
            favouriteSermons.isEmpty() ||
            favouriteSermons.isNotEmpty() && filterFavourites.not()
        }
        if (filterOldest) {
            filtered.sortedBy { it.dateMillis }
        } else {
            filtered.sortedBy { it.dateMillis }.reversed()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    private val _canLoadMoreSermons = MutableStateFlow(false)
    val canLoadMoreSermons = _canLoadMoreSermons.asStateFlow()

    var currentSermon by mutableStateOf<Sermon?>(null)


    fun onSearchTermChanged(searchTerm: String) {
        viewModelScope.launch {
            launch {
                _searchTerm.value = searchTerm
            }
            delay(2000)
            if (_canLoadMoreSermons.value) {
                loadMoreSermons()
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
    fun fetchSermons() {
        currentPage = 1
        sermonController.getSermon(currentPage) {
            when (it) {
                is DataResponse.Failure -> Log.d("SERMON/FAILURE", "${it.message}")
                is DataResponse.Success -> {
                    sermons.value = it.data?.sermons ?: emptyList()
                    currentPage = (it.data?.pageNumber ?: 1)
                    _canLoadMoreSermons.value = it.data?.canLoadMoreSermons ?: false
                    Log.d("SERMON/SUCCESS", "${it.data}")
                }

                else -> {}
            }
        }
    }


    fun loadMoreSermons() {
        currentPage++
        sermonController.getSermon(currentPage) {
            when (it) {
                is DataResponse.Failure -> {
                    _canLoadMoreSermons.value = false
                    Log.d("SERMON/FAILURE", "${it.message}")
                }
                is DataResponse.Success -> {
                    sermons.value += it.data?.sermons ?: emptyList()
                    _canLoadMoreSermons.value = it.data?.canLoadMoreSermons ?: false
                    Log.d("SERMON/SUCCESS", "${it.data}")
                }

                else -> {}
            }
        }
    }

    fun setFilterFavourites(filterFavourites: Boolean) {
        _filterFavourites.value = filterFavourites
        _canLoadMoreSermons.value = filterFavourites.not()
        _filterOldest.value = false
        _filterNewest.value = false
    }

    fun setFilterNewest(filterNewest: Boolean) {
        _filterNewest.value = filterNewest
        _canLoadMoreSermons.value = true
        _filterFavourites.value = false
        _filterOldest.value = false
    }

    fun setFilterOldest(filterOldest: Boolean) {
        _filterOldest.value = filterOldest
        _filterFavourites.value = false
        _filterNewest.value = false
    }

    fun addSermon(sermon: MomentumSermon, onCompletion: () -> Unit) {
        sermonController.addSermon(sermon = sermon)
        onCompletion()
    }

    fun getCurrentSermon(): MomentumSermon? {
        return _watchedSermons.value.firstOrNull {
            it.id == (currentSermon?.id ?: "")
        }
    }

    fun getFavouriteSermons(){
        sermonController.getFavouriteSermons {
            _favouriteSermon.value = it.toMutableList()
            Log.d("Sermons", "$it")
        }
    }
    fun addFavourite(id: String) {
        sermonController.addFavouriteSermon(id)
        _favouriteSermon.value += SermonFavourite(id, "")
    }

    fun removeFavourite(id: String) {
        sermonController.removeFavouriteSermon(id)
        _favouriteSermon.value.removeIf { it.id == id }

    }

    fun getWatchSermons(onCompletion: () -> Unit = {}) {
        sermonController.getWatchedSermons { sermons ->
            _watchedSermons.value = sermons
        }
        onCompletion()
    }
    companion object {
        var searchTags = mutableListOf(
            "by sermon",
            "for preachers",
            "by series name",
            "by date"
        )
    }
}