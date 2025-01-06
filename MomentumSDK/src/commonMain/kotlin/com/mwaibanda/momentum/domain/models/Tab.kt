package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tab(
    val id: String,
    val name: String,
    val type: Type,
    val image: String,
) {
    @Serializable
    enum class Type {
        @SerialName("MEALS") MEALS,
        @SerialName("CLEANING") CLEANING,
        @SerialName("LANDSCAPING") LANDSCAPING,
        @SerialName("MAINTENANCE") MAINTENANCE,
        @SerialName("GREETING") GREETING,
        @SerialName("TECH") TECH_TEAM,
        @SerialName("KIDS") KIDS,
        @SerialName("MUSIC") MUSIC,
    }

}

val Tab.Type.value: String
    get() {
        return when(this){
            Tab.Type.MEALS -> "MEALS"
            Tab.Type.CLEANING -> "CLEANING"
            Tab.Type.LANDSCAPING -> "LANDSCAPING"
            Tab.Type.MAINTENANCE -> "MAINTENANCE"
            Tab.Type.GREETING -> "GREETING"
            Tab.Type.TECH_TEAM -> "TECH"
            Tab.Type.KIDS -> "KIDS"
            Tab.Type.MUSIC -> "MUSIC"
        }
    }