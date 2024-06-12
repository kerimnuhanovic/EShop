package com.eshop.chat_presentation.conversations

import com.eshop.chat_domain.model.Conversation
import com.eshop.coreui.util.BottomBarItem

data class ConversationsState(
    val conversations: List<Conversation> = emptyList(),
    val filteredConversations: List<Conversation> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val bottomBarItems: List<BottomBarItem> = emptyList()
)
