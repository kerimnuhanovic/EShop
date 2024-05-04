package com.eshop.chat_domain.usecase

import com.eshop.chat_domain.repository.ChatClient
import javax.inject.Inject

class ConnectToSocketUseCase @Inject constructor(
    private val chatClient: ChatClient
) {
    suspend operator fun invoke() {
        chatClient.connect()
    }
}