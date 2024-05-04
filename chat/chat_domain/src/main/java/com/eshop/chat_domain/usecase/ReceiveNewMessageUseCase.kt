package com.eshop.chat_domain.usecase

import com.eshop.chat_domain.model.Message
import com.eshop.chat_domain.repository.ChatClient
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ReceiveNewMessageUseCase @Inject constructor(
    private val chatClient: ChatClient
) {
    operator fun invoke(): StateFlow<Message?> {
        return chatClient.newMessage
    }
}