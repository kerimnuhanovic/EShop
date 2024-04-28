package com.eshop.chat_domain.model

data class Conversation(
    val chatPartner: String,
    val chatPartnerProfileImage: String,
    val messages: List<Message>
)
