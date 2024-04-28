package com.eshop.chat_presentation

sealed interface ChatEvent {
    object OnMessageSend : ChatEvent
    data class OnSearchQueryEnter(val query: String) : ChatEvent
}