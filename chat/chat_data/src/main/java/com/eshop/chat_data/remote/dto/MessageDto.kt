package com.eshop.chat_data.remote.dto

data class MessageDto(val sentBy: String, val receivedBy: String, val payload: String)
