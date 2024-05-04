package com.eshop.chat_domain.usecase

import com.eshop.chat_domain.repository.ChatClient
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val chatClient: ChatClient
) {
    suspend operator fun invoke(receiverUsername: String, payload: String) {
        return chatClient.sendMessage(receiverUsername, payload)
    }
}