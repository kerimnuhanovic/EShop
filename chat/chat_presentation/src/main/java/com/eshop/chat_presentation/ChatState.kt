package com.eshop.chat_presentation

import com.eshop.chat_domain.model.Conversation

data class ChatState(
    val conversations: List<Conversation> = emptyList(),
    val filteredConversations: List<Conversation> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
)
