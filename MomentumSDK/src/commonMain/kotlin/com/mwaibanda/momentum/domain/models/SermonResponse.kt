package com.mwaibanda.momentum.domain.models

data class SermonResponse (
    val sermons: List<Sermon>,
    val canLoadMoreSermons: Boolean
)