package com.eshop.chat_presentation.conversations

sealed interface ConversationsEvent {
    data class OnSearchQueryEnter(val query: String) : ConversationsEvent
    data class OnConversationClick(val chatPartner: String) : ConversationsEvent
}