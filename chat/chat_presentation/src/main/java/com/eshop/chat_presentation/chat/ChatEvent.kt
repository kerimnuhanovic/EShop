package com.eshop.chat_presentation.chat

sealed interface ChatEvent {
    object OnBackClick : ChatEvent
    data class OnMessageEnter(val message: String) : ChatEvent
    object OnSendMessageClick : ChatEvent
}