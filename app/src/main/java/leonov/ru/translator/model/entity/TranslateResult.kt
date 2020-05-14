package leonov.ru.translator.model.entity

data class TranslateResult (
    val text: String,
    val translation: String,
    val partOfSpeechCode: String,
    val previewUrl: String,
    val transcription: String,
    val soundUrl: String
)