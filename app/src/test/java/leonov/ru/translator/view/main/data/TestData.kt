package leonov.ru.translator.view.main.data//package leonov.ru.translator.view.main

import leonov.ru.translator.model.data.Meanings
import leonov.ru.translator.model.data.SearchResult
import leonov.ru.translator.model.data.Translation
import leonov.ru.translator.room.HistoryEntity

val translation = Translation("trans1", "note1")
val meanings = listOf(Meanings(translation, "", "", "", "", ""))

val searchResult = SearchResult("word1", meanings)

val searchResultList = listOf(
    SearchResult("word1", meanings),
    SearchResult("word2", meanings)
)

val historyEntity1 = HistoryEntity("word1", "translation1", "transcription1")
val historyEntity2 = HistoryEntity("word2", "translation2", "transcription2")
val historyEntityList = listOf<HistoryEntity>(historyEntity1, historyEntity2)
