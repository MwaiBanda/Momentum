package com.mwaibanda.momentum.domain.models

data class SermonResponse (
    val pageNumber: Int,
    val sermons: List<Sermon>,
    val canLoadMoreSermons: Boolean
)