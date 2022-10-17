package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Sermon(
    val id: String,
    val series: String,
    val title: String,
    val preacher: String,
    val videoThumbnail: String,
    val videoURL: String,
    val date: String,
    val dateMillis: Long
)
