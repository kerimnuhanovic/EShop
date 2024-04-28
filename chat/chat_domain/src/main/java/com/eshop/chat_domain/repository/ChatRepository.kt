package com.eshop.chat_domain.repository

import com.eshop.chat_domain.model.Conversation
import com.eshop.core.util.Result

interface ChatRepository {
    suspend fun fetchUserConversations(): Result<List<Conversation>>
}