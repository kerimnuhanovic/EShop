package com.eshop.chat_data.remote

import com.eshop.chat_data.remote.dto.ConversationDto
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ChatApi {
    @GET("chat/conversations")
    suspend fun fetchUserConversations(): List<ConversationDto>

    @PATCH("chat/conversations/{chatPartner}")
    suspend fun fetchAndUpdateUserConversation(@Path("chatPartner") chatPartner: String): ConversationDto
}