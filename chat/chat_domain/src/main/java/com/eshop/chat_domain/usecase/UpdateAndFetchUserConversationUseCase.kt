package com.eshop.chat_domain.usecase

import com.eshop.chat_domain.model.Conversation
import com.eshop.chat_domain.repository.ChatRepository
import com.eshop.core.util.Result
import javax.inject.Inject

class UpdateAndFetchUserConversationUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(chatPartner: String): Result<Conversation> {
        return chatRepository.updateAndFetchUserConversation(chatPartner)
    }
}