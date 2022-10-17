package com.mwaibanda.momentum.domain.controller

import com.mwaibanda.momentum.data.db.MomentumSermon
import com.mwaibanda.momentum.data.db.SermonFavourite
import com.mwaibanda.momentum.domain.models.SermonResponse
import com.mwaibanda.momentum.utils.Result

interface SermonController {
    fun getSermon(pageNumber: Int, onCompletion: (Result<SermonResponse>) -> Unit)
    fun addSermon(sermon: MomentumSermon)
    fun getWatchedSermons(onCompletion: (List<MomentumSermon>) -> Unit)
    fun addFavouriteSermon(id: String)
    fun removeFavouriteSermon(id: String)
    fun getFavouriteSermons(onCompletion: (List<SermonFavourite>) -> Unit)
}