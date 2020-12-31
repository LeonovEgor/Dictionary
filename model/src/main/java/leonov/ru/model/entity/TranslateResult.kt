package leonov.ru.model.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class TranslateResult (
    val text: String,
    val translation: String,
    val note: String,
    val partOfSpeech: String,
    val previewUrl: String,
    val imageUrl: String,
    val transcription: String,
    val soundUrl: String
): Parcelable