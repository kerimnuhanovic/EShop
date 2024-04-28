package com.eshop.chat_data.remote

import com.eshop.chat_data.remote.dto.ConversationDto
import retrofit2.http.GET

interface ChatApi {
    @GET("chat/conversations")
    suspend fun fetchUserConversations(): List<ConversationDto>
}