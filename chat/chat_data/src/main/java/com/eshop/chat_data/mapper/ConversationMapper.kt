package com.eshop.chat_data.mapper

import com.eshop.chat_data.remote.dto.ConversationDto
import com.eshop.chat_domain.model.Conversation

fun ConversationDto.toConversation(): Conversation {
    return Conversation(
        chatPartner = this.chatPartner,
        chatPartnerProfileImage = this.chatPartnerProfileImage,
        messages = this.messages.map { receivedMessageDto ->
            receivedMessageDto.toMessage()
        }
    )
}