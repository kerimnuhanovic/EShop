package com.eshop.chat_data.repository

import com.eshop.chat_data.mapper.toConversation
import com.eshop.chat_data.remote.ChatApi
import com.eshop.chat_domain.model.Conversation
import com.eshop.chat_domain.repository.ChatRepository
import com.eshop.core.util.Result
import com.eshop.core.util.handleApiError
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatApi: ChatApi
): ChatRepository {
    override suspend fun fetchUserConversations(): Result<List<Conversation>> {
        return try {
            val result = chatApi.fetchUserConversations()
            val conversations = result.map { conversationDto ->
                conversationDto.toConversation()
            }
            Result.Success(conversations)
        } catch(ex: Exception) {
            handleApiError(ex)
        }
    }
}