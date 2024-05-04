package com.eshop.chat_data.repository

import com.eshop.chat_data.mapper.toMessage
import com.eshop.chat_data.remote.dto.MessageDto
import com.eshop.chat_data.remote.dto.ReceivedMessageDto
import com.eshop.chat_data.utils.SocketEvent
import com.eshop.chat_domain.model.Message
import com.eshop.chat_domain.repository.ChatClient
import com.eshop.core.domain.preferences.Preferences
import com.eshop.core.util.BASE_URL
import com.squareup.moshi.Moshi
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.EngineIOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ChatClientImpl @Inject constructor(
    private val preferences: Preferences,
    private val moshi: Moshi
): ChatClient {
    private val socket: Socket = IO.socket(BASE_URL)

    private val _newMessage: MutableStateFlow<Message?> = MutableStateFlow(null)
    override val newMessage: StateFlow<Message?> = _newMessage.asStateFlow()

    override suspend fun connect() {
        socket.connect()

        socket.on(Socket.EVENT_CONNECT_ERROR) {
            val ex = it[0] as EngineIOException
            println(ex.message)
        }

        socket.on(Socket.EVENT_CONNECT) {
            socket.emit(SocketEvent.REGISTER_USER.eventName, preferences.readToken()!!)
        }

        socket.on(SocketEvent.RECEIVE_MESSAGE.eventName) { data ->
            val jsonObject = data[0]
            val adapter = moshi.adapter(ReceivedMessageDto::class.java)
            val messageReceived = adapter.fromJson(jsonObject.toString()) as ReceivedMessageDto
            _newMessage.value = messageReceived.toMessage()
        }

    }

    override suspend fun sendMessage(receiverUsername: String, payload: String) {
        val adapter = moshi.adapter(MessageDto::class.java)
        socket.emit(SocketEvent.SEND_MESSAGE.eventName, adapter.toJson(MessageDto(preferences.readToken()!!, receiverUsername, payload)))
    }
}