package com.eshop.chat_presentation.chat

import com.eshop.chat_domain.model.Conversation

data class ChatState(
    val conversation: Conversation? = null,
    val isLoading: Boolean = false,
    val message: String = ""
)
