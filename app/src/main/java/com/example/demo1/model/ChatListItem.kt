package com.example.demo1.model

import com.hyphenate.chat.EMMessage

data class ChatListItem(
    var imageSrc: String = "",
    var chatName: String,
    var conversationId: String,
) {
    constructor(message: EMMessage) : this("", "", "") {
        chatName = message.from
        conversationId = message.conversationId()
    }
}
