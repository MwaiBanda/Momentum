package com.mwaibanda.momentum.data.sermonDTO

import com.mwaibanda.momentum.domain.models.Sermon
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SermonDTO(
    val categories: HashMap<String, String>?,
    @SerialName("date")
    val dateDTO: DateDTO,
    val detail_url: String,
    val id: String,
    val interactions: InteractionsDTO,
    val interactive_note: Boolean?,
    val keywords: String?,
    @SerialName("media")
    val mediaDTO: MediaDTO,
    val passages: List<String?>,
    val preacher: String,
    @SerialName("series")
    val seriesDTO: SeriesDTO,
    val slug: String?,
    val summary: String?,
    val text: String?,
    val title: String
)

fun SermonDTO.toSermon(): Sermon {
    return Sermon(
        id = id,
        series = seriesDTO.title?.replace("It&#039;s", "") ?: seriesDTO.title ?: "",
        title = title,
        preacher = preacher,
        videoThumbnail = mediaDTO.video_thumbnail,
        videoURL = mediaDTO.video,
        date = dateDTO.date
    )
}