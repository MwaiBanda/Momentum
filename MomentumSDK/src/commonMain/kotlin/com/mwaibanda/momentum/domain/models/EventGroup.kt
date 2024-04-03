package com.mwaibanda.momentum.domain.models

data class EventGroup(
    val id: String,
    val monthAndYear: String,
    val events: List<Event>
) {
    fun containsTerm(term: String): Boolean {
        return monthAndYear.lowercase().contains(term.lowercase()) ||
                events.count { event ->
                    event.name.lowercase().contains(term.lowercase()) ||
                            event.getFormattedStartDate().lowercase().contains(term.lowercase())
                } > 0 ||
                term.isEmpty()
    }
}
