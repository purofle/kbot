package io.github.purofle.kbot.utils

object StringUtils {
    fun String.escapeText(): String {
        val specialChars = arrayOf(
            "\\", "_", "*", "[", "]", "(", ")", "~", "`", ">", "#", "+", "-", "=", "|", "{",
            "}", ".", "!"
        )

        var text = this
        for (specialCharacter in specialChars) {
            text = text.replace(specialCharacter, "\\" + specialCharacter)
        }

        return text
    }
}