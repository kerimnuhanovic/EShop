package com.eshop.chat_data.utils

enum class SocketEvent(val eventName: String) {
    REGISTER_USER("register_user"),
    SEND_MESSAGE("send_message"),
    RECEIVE_MESSAGE("receive_message"),
}