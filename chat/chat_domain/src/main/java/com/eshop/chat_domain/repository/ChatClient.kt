package com.eshop.chat_domain.repository

import com.eshop.chat_domain.model.Message
import kotlinx.coroutines.flow.StateFlow

interface ChatClient {
    val newMessage: StateFlow<Message?>
    suspend fun connect()
    suspend fun sendMessage(receiverUsername: String, payload: String)
}