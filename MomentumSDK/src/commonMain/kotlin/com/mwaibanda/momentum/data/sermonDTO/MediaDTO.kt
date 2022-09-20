package com.mwaibanda.momentum.data.sermonDTO

import kotlinx.serialization.Serializable

@Serializable
data class MediaDTO(
    val audio: String?,
    val embed: String?,
    val image: String?,
    val notes: String?,
    val video: String,
    val video_thumbnail: String
)