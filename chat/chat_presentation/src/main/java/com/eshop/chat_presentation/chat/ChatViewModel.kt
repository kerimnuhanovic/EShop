package com.eshop.chat_presentation.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshop.chat_domain.model.Conversation
import com.eshop.chat_domain.usecase.ReceiveNewMessageUseCase
import com.eshop.chat_domain.usecase.SendMessageUseCase
import com.eshop.chat_domain.usecase.UpdateAndFetchUserConversationUseCase
import com.eshop.core.util.Result
import com.eshop.coreui.navigation.Route
import com.eshop.coreui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val updateAndFetchUserConversationUseCase: UpdateAndFetchUserConversationUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val receiveNewMessageUseCase: ReceiveNewMessageUseCase
): ViewModel() {

    private val chatPartner: String = checkNotNull(savedStateHandle["chatPartner"])

    private val _state: MutableStateFlow<ChatState> = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        fetchConversation()
    }

    fun onEvent(event: ChatEvent) {
        when (event) {
            ChatEvent.OnBackClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Route.CONVERSATIONS))
                }
            }

            is ChatEvent.OnMessageEnter -> {
                _state.value  = state.value.copy(
                    message = event.message
                )
            }
            ChatEvent.OnSendMessageClick -> {
                sendMessage()
            }
        }
    }

    private fun fetchConversation() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = state.value.copy(
                isLoading = true
            )
            when (val result = updateAndFetchUserConversationUseCase(chatPartner)) {
                is Result.Success -> {
                    _state.value = state.value.copy(
                        conversation = result.data,
                        isLoading = false
                    )
                    receiveNewMessages()
                }
                is Result.Failure -> {

                }
            }
        }
    }

    private fun receiveNewMessages() {
        if (state.value.conversation == null) {
            return
        }
        viewModelScope.launch {
            receiveNewMessageUseCase().collect { message ->
                if (message != null) {
                    val conversation = Conversation(
                        chatPartner = state.value.conversation!!.chatPartner,
                        chatPartnerProfileImage = state.value.conversation!!.chatPartnerProfileImage,
                        messages = state.value.conversation!!.messages.plus(message)
                    )
                    _state.value = state.value.copy(
                        message = "",
                        conversation = conversation
                    )
                }
            }
        }
    }

    private fun sendMessage() {
        viewModelScope.launch {
            sendMessageUseCase.invoke(chatPartner, state.value.message)
        }
    }

}