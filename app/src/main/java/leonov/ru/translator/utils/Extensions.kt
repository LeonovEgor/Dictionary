package leonov.ru.translator.utils

import leonov.ru.translator.model.data.PartOfSpeechEnum
import java.lang.Exception

fun String.Companion.getEmptyString(): String = ""

private const val leftBracket = "("
private const val rightBracket = ")"

fun String.surroundBrackets(): String = "$leftBracket${this}$rightBracket"

private const val httpsPrefix = "https:"

fun String.addHttpsPrefix(): String = "$httpsPrefix${this}"

fun String.getPartOfSpeech() =
    try {
        PartOfSpeechEnum.valueOf(this).value
    } catch (e: Exception) {""}