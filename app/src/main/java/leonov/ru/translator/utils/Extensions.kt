package leonov.ru.translator.utils

import leonov.ru.translator.model.data.Meanings
import leonov.ru.translator.model.data.PartOfSpeechEnum
import leonov.ru.translator.model.entity.TranslateResult
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


fun List<TranslateResult>.convertTranslationToCommaString(): String {
    var separatedByCommaString = String()
    for ((index, translateResult) in this.withIndex()) {
        separatedByCommaString += if (index + 1 != this.size) {
            String.format("%s%s", translateResult.translation, ", ")
        } else {
            translateResult.translation
        }
    }
    return separatedByCommaString
}