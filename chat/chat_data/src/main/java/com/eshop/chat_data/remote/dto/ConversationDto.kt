package com.eshop.chat_data.remote.dto

data class ConversationDto(
    val chatPartner: String,
    val chatPartnerProfileImage: String,
    val messages: List<ReceivedMessageDto>
)