package leonov.ru.translator.utils

import leonov.ru.model.data.SearchResult
import leonov.ru.model.entity.TranslateResult
import ru.leonov.repository.room.HistoryEntity

fun mapTranslateResultListToHistoryEntity(
    word: String,
    translateResultList: ArrayList<TranslateResult>
): HistoryEntity {
    return HistoryEntity(
        word,
        translateResultList.convertTranslationToCommaString(),
        ""
    )
}

fun mapSearchResultToTranslateResult(
    searchResult: SearchResult,
    translateResultList: MutableCollection<TranslateResult>
) {
    searchResult.meanings?.let { meaningList ->
        meaningList.forEach { meaning ->
            val partOfSpeech = meaning.partOfSpeechCode?.getPartOfSpeech() ?: ""

            translateResultList.add(
                TranslateResult(
                    searchResult.text ?: "",
                    meaning.translation?.text ?: "",
                    meaning.translation?.note ?: "",
                    partOfSpeech,
                    meaning.previewUrl ?: "",
                    meaning.imageUrl ?: "",
                    meaning.transcription ?: "",
                    meaning.soundUrl ?: ""
                )
            )
        }
    }
}

fun mapHistoryEntityToTranslateResult(
    historyEntity: HistoryEntity,
    translateResultList: ArrayList<TranslateResult>
) {
    translateResultList.add(
        TranslateResult(
            historyEntity.word,
            historyEntity.translation,
            "",
            "",
            "",
            "",
            historyEntity.transcription,
            ""
        )
    )
}