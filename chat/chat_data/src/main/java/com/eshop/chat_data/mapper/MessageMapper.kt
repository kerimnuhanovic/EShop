package com.eshop.chat_data.mapper

import com.eshop.chat_data.remote.dto.ReceivedMessageDto
import com.eshop.chat_domain.model.Message
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun ReceivedMessageDto.toMessage(): Message {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val dateMessageIsSent = LocalDate.parse(this.dateCreated, inputFormatter)
    return Message(
        sentBy = this.sentBy,
        receivedBy = this.receivedBy,
        payload = this.payload,
        dateCreated = dateMessageIsSent,
        isSeen = this.isSeen,
        isCurrentUserReceiver = this.isCurrentUserReceiver
    )
}