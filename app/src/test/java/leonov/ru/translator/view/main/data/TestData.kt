package leonov.ru.translator.view.main.data//package leonov.ru.translator.view.main

import leonov.ru.model.data.Meanings
import leonov.ru.model.data.SearchResult
import leonov.ru.model.data.Translation
import ru.leonov.repository.room.HistoryEntity

val translation = leonov.ru.model.data.Translation("trans1", "note1")
val meanings = listOf(
    leonov.ru.model.data.Meanings(
        translation,
        "",
        "",
        "",
        "",
        ""
    )
)

val searchResult = leonov.ru.model.data.SearchResult("word1", meanings)

val searchResultList = listOf(
    leonov.ru.model.data.SearchResult("word1", meanings),
    leonov.ru.model.data.SearchResult("word2", meanings)
)

val historyEntity1 = ru.leonov.repository.room.HistoryEntity(
    "word1",
    "translation1",
    "transcription1"
)
val historyEntity2 = ru.leonov.repository.room.HistoryEntity(
    "word2",
    "translation2",
    "transcription2"
)
val historyEntityList = listOf<ru.leonov.repository.room.HistoryEntity>(historyEntity1, historyEntity2)
