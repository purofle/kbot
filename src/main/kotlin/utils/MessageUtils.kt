package io.github.purofle.kbot.utils

object MessageUtils {
    /**
     * Check if the message is a bot command
     * @param text The text of the message
     * @param username The username of the bot
     */
    fun isBotCommand(text: String, username: String): Boolean {

        if (!text.startsWith("/")) {
            return false
        }

        // ["/xxx@xxbot", args]
        val messageCommands = text.split(" ")
        // ["/xxx", "xxbot"]
        val commandWithAt = messageCommands[0].split("@")

        return if (commandWithAt.size >= 2) {
            // Check if the command is for this bot
            commandWithAt[1] == username
        } else {
            true
        }
    }

    fun getCommandFromMessage(message: String): List<String> {
        val msgSplit = message.split(" ").toMutableList()
        if (msgSplit[0].contains("@")) {
            msgSplit[0] = msgSplit[0].substringBefore("@")
        }

        return msgSplit
    }
}

enum class ParseMode(val field: String) {
    MARKDOWN_V2("MarkdownV2"),
    HTML("HTML"),
    MARKDOWN("Markdown"),
    // None is a special case, it means no parse mode
    None("None")
}