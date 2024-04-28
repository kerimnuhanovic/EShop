package com.eshop.chat_data.remote.dto

data class ReceivedMessageDto(
    val sentBy: String,
    val receivedBy: String,
    val payload: String,
    val dateCreated: String,
    val isSeen: Boolean,
    val isCurrentUserReceiver: Boolean? = null
)
