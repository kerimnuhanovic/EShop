package com.eshop.chat_presentation.conversations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eshop.chat_domain.model.Conversation
import com.eshop.chat_domain.usecase.ConnectToSocketUseCase
import com.eshop.chat_domain.usecase.FetchUserConversationsUseCase
import com.eshop.chat_domain.usecase.ReceiveNewMessageUseCase
import com.eshop.chat_domain.usecase.SendMessageUseCase
import com.eshop.core.domain.preferences.Preferences
import com.eshop.core.util.Result
import com.eshop.coreui.navigation.Route
import com.eshop.coreui.util.UiEvent
import com.eshop.coreui.util.generateBottomBarItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor(
    private val connectToSocketUseCase: ConnectToSocketUseCase,
    private val fetchUserConversationsUseCase: FetchUserConversationsUseCase,
    private val receiveNewMessageUseCase: ReceiveNewMessageUseCase,
    private val preferences: Preferences
) : ViewModel() {

    private val _state: MutableStateFlow<ConversationsState> = MutableStateFlow(ConversationsState(isLoading = true))
    val state: StateFlow<ConversationsState> = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        generateBarItems()
        fetchUserConversations()
    }

    fun onEvent(event: ConversationsEvent) {
        when (event) {
            is ConversationsEvent.OnSearchQueryEnter -> {
                _state.value = state.value.copy(
                    searchQuery = event.query,
                    filteredConversations = state.value.conversations.filter { conversation ->
                        conversation.chatPartner.lowercase().contains(event.query.lowercase())
                    }
                )
            }

            is ConversationsEvent.OnConversationClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate("${Route.CHAT}/${event.chatPartner}"))
                }
            }
        }
    }

    private fun fetchUserConversations() {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            when (val result = fetchUserConversationsUseCase()) {
                is Result.Success -> {
                    connectToSocketUseCase()
                    _state.value = state.value.copy(
                        conversations = result.data,
                        filteredConversations = result.data,
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
        viewModelScope.launch {
            receiveNewMessageUseCase().collect { message ->
                if (message != null) {
                    val conversations = state.value.conversations.map { conversation ->
                        Conversation(
                            chatPartner = conversation.chatPartner,
                            chatPartnerProfileImage = conversation.chatPartnerProfileImage,
                            messages = if (conversation.chatPartner == message.sentBy) conversation.messages.plus(message) else conversation.messages
                        )
                    }
                    _state.value = state.value.copy(
                        conversations = conversations,
                        filteredConversations = conversations
                    )
                }
            }
        }
    }

    private fun generateBarItems() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                bottomBarItems = generateBottomBarItems(preferences.readUserType()!!.type)
            )
        }
    }
}