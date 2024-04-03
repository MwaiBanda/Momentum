package com.mwaibanda.momentum.domain.models

data class MessageGroup(
    val id: String,
    val series: String,
    val messages: List<Message>
) {
    fun containsTerm(term: String): Boolean {
        return series.lowercase().contains(term.lowercase()) ||
               messages.count { message ->
                    message.preacher.lowercase().contains(term.lowercase()) ||
                            message.date.lowercase().contains(term.lowercase())  ||
                            message.title.lowercase().contains(term.lowercase())
               } > 0 ||
               term.isEmpty()
    }
}
