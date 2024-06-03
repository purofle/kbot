package io.github.purofle.kbot

import io.github.purofle.kbot.utils.MessageUtils.getCommandFromMessage
import io.github.purofle.kbot.utils.MessageUtils.isBotCommand
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.methods.GetMe
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.User

/**
 * The main bot class
 * @param token The bot token
 * @param scope The coroutine scope to use
 */
class Bot(
    token: String,
    private var scope: CoroutineScope = CoroutineScope(Dispatchers.IO) + SupervisorJob(),
) : LongPollingSingleThreadUpdateConsumer, CoroutineScope {

    private val logger = LoggerFactory.getLogger(this::class.java)

    private val handler = CoroutineExceptionHandler { _, exception ->
        logger.error(exception.stackTraceToString())
    }
    override val coroutineContext = scope.coroutineContext + handler

    private val telegramClient by lazy {
        OkHttpTelegramClient(token)
    }
    private var onNewMessageHandler: MessageHandler? = null
    private val commands = mutableMapOf<String, MessageHandler>()
    private val botUser: User by lazy {
        telegramClient.execute(GetMe())
    }

    override fun consume(update: Update) {

        if (update.hasMessage()) {
            if (update.message.from.id == botUser.id) {
                // Ignore messages from the bot itself
                return
            }

            if (update.message.text == null) {
                // Ignore non-text messages
                return
            }

            val message = update.message
            val handler = NewMessageHandler(telegramClient, message)

            onNewMessageHandler?.let { callback ->

                scope.launch {
                    callback.invoke(handler)
                }
            }

            if (message.hasText() && message.text.startsWith("/")) {

                if (!isBotCommand(message.text, botUser.userName)) {
                    return
                }

                val command = getCommandFromMessage(message.text)[0]

                if (commands.filter { command.startsWith(it.key) }.isNotEmpty()) {
                    // Call the command handler
                    scope.launch {
                        commands[command]?.invoke(handler)
                    }
                }
            }
        }
    }

    /**
     * Register a new message handler
     * @param handler The handler to register
     */
    fun onNewTextMessage(handler: MessageHandler) {
        onNewMessageHandler = handler
    }

    /**
     * Register a new command handler
     * @param command The command to register
     */
    fun command(command: String, callback: MessageHandler) {
        logger.debug("Registering command: $command")
        commands[command] = callback
    }
}