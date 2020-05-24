package leonov.ru.utils

private const val httpsPrefix = "https:"
private const val leftBracket = "("
private const val rightBracket = ")"

fun String.addHttpsPrefix(): String = "$httpsPrefix${this}"

fun String.surroundBrackets(): String = "$leftBracket${this}$rightBracket"
