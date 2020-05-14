package leonov.ru.translator.model.data

import com.google.gson.annotations.Expose

class Meanings(
    @Expose val translation: Translation?,
    @Expose val partOfSpeechCode: String?,
    @Expose val previewUrl: String?,
    @Expose val transcription: String?,
    @Expose val soundUrl: String?,
    @Expose val imageUrl: String?
)
