package com.mwaibanda.momentum.domain.models

import com.mwaibanda.momentum.utils.CommonParcelable
import com.mwaibanda.momentum.utils.CommonParcelize
import kotlinx.serialization.Serializable

@Serializable
@CommonParcelize
data class Sermon(
    val id: String,
    val series: String,
    val title: String,
    val preacher: String,
    val videoThumbnail: String,
    val videoURL: String,
    val date: String,
    val dateMillis: Long
): CommonParcelable
