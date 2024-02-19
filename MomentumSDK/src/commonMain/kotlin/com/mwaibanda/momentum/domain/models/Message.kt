package com.mwaibanda.momentum.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val id: String,
    val thumbnail: String,
    val series: String,
    val title: String,
    val preacher: String,
    val date: String,
    val createdOn: String,
    val passages: List<Passage>
) {
    fun containsTerm(term: String): Boolean {
        return preacher.lowercase().contains(term.lowercase()) ||
               date.lowercase().contains(term.lowercase()) ||
               title.lowercase().contains(term.lowercase()) ||
               term.isEmpty()
    }
}