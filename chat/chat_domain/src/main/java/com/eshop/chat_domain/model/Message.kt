package com.eshop.chat_domain.model

import java.time.LocalDate

data class Message(
    val sentBy: String,
    val receivedBy: String,
    val payload: String,
    val dateCreated: LocalDate,
    val isSeen: Boolean,
    val isCurrentUserReceiver: Boolean? = null
)
