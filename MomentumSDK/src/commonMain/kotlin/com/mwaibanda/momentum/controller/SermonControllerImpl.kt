package com.mwaibanda.momentum.controller

import com.mwaibanda.momentum.data.db.Database
import com.mwaibanda.momentum.data.db.DatabaseDriverFactory
import com.mwaibanda.momentum.data.db.MomentumSermon
import com.mwaibanda.momentum.data.db.SermonFavourite
import com.mwaibanda.momentum.domain.controller.SermonController
import com.mwaibanda.momentum.domain.models.SermonResponse
import com.mwaibanda.momentum.domain.usecase.sermon.GetSermonsUseCase
import com.mwaibanda.momentum.utils.DataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SermonControllerImpl(
    driverFactory: DatabaseDriverFactory
): SermonController, KoinComponent {

    private val getSermonsUseCase: GetSermonsUseCase by inject()
    private val database = Database(driverFactory)
    private val scope: CoroutineScope = MainScope()
    override fun getSermon(pageNumber: Int, onCompletion: (DataResponse<SermonResponse>) -> Unit) {
        scope.launch {
            getSermonsUseCase(pageNumber = pageNumber) {
                onCompletion(it)
            }
        }
    }

    override fun addSermon(sermon: MomentumSermon) {
        database.addSermon(
            id = sermon.id,
            lastPlayedTime = sermon.last_played_time,
            lastPlayedPercentage = sermon.last_played_percentage
        )
    }

    override fun getWatchedSermons(onCompletion: (List<MomentumSermon>) -> Unit) {
        onCompletion(database.getWatchedSermons())
    }

    override fun addFavouriteSermon(id: String) {
        database.addFavouriteSermon(id = id)
    }

    override fun removeFavouriteSermon(id: String) {
        database.deleteFavouriteSermon(id = id)
    }

    override fun getFavouriteSermons(onCompletion: (List<SermonFavourite>) -> Unit) {
        onCompletion(database.getFavouriteSermons())
    }
}