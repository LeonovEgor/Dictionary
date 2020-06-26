package leonov.ru.translator.view.main.data

import leonov.ru.model.data.Meanings
import leonov.ru.model.data.SearchResult
import leonov.ru.model.data.Translation
import ru.leonov.repository.room.HistoryEntity

val translation = Translation("trans1", "note1")
val meanings = listOf(
    Meanings(
        translation,
        "",
        "",
        "",
        "",
        ""
    )
)

val searchResult1 = SearchResult("word1", meanings)
val searchResult2 = SearchResult("word1", meanings)

val searchResultList = listOf(
    searchResult1,
    searchResult2
)

val historyEntity1 = HistoryEntity(
    "word1",
    "translation1",
    "transcription1"
)
val historyEntity2 = HistoryEntity(
    "word2",
    "translation2",
    "transcription2"
)
val historyEntityList = listOf(historyEntity1, historyEntity2)
