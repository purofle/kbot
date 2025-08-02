package io.github.purofle.kbot.utils

import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.chat.Chat
import org.telegram.telegrambots.meta.api.objects.message.Message
import org.telegram.telegrambots.meta.generics.TelegramClient

object ClientUtils {
    fun TelegramClient.reply(message: Message, text: String, futures: SendMessage.() -> Unit) {
        val msg = SendMessage.builder().apply {
            chatId(message.chatId.toString())
            replyToMessageId(message.messageId)

            text(text)
        }.build()

        msg.futures()

        execute(msg)
    }

    fun TelegramClient.getUserByUsername(chatId: String): Chat {
        return execute(GetChat(chatId))
    }
}
