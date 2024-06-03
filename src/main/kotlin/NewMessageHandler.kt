package io.github.purofle.kbot

import io.github.purofle.kbot.utils.ClientUtils.reply
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.message.Message

// The type of message handler
typealias MessageHandler = suspend NewMessageHandler.() -> Unit

/**
 * The message handler
 * @param client The telegram client
 * @param message The message
 */
class NewMessageHandler(
    val client: OkHttpTelegramClient,
    val message: Message,
) {

    /**
     * Reply to the context message
     * @param text The text to reply with
     */
    fun reply(text: String, futures: SendMessage.() -> Unit = {}) = client.reply(message, text, futures)
}