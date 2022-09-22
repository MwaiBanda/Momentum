package com.mwaibanda.momentum.data.sermonDTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SermonContainerDTO(
    val current_page: Int,
    @SerialName("data")
    val sermonDTOS: List<SermonDTO>,
    val first_page_url: String,
    val from: Int,
    val last_page: Int,
    val last_page_url: String,
    @SerialName("links")
    val linkDTOS: List<LinkDTO>,
    val next_page_url: String?,
    val path: String,
    val per_page: String,
    val prev_page_url: String?,
    val to: Int,
    val total: String
)