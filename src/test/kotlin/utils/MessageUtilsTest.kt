package utils

import io.github.purofle.kbot.utils.MessageUtils.getCommandFromMessage
import io.github.purofle.kbot.utils.MessageUtils.isBotCommand
import kotlin.test.Test
import kotlin.test.assertTrue

internal class MessageUtilsTest {

    @Test
    fun `test bot command`() {
        val testText = "/start"
        check(isBotCommand(testText, "test_bot"))
    }

    @Test
    fun `test bot command with username`() {
        check(isBotCommand("/start@test_bot", "test_bot"))
        check(!isBotCommand("/start@test_bot", "test2_bot"))
    }

    @Test
    fun `test getCommandFromMessage with username and args`() {
        assertTrue {
            getCommandFromMessage("/start@test_bot arg1 arg2") == listOf("/start", "arg1", "arg2")
        }
    }
}