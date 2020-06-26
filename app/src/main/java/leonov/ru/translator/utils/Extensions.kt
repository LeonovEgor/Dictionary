package leonov.ru.translator.utils

import leonov.ru.model.data.PartOfSpeechEnum
import leonov.ru.model.entity.TranslateResult
import java.lang.Exception

fun String.Companion.getEmptyString(): String = ""

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