package leonov.ru.translator.view.main.data//package leonov.ru.translator.view.main

import leonov.ru.translator.model.data.Meanings
import leonov.ru.translator.model.data.SearchResult
import leonov.ru.translator.model.data.Translation

val translation = Translation("trans1")
val meanings = listOf(Meanings(translation, "", "", "", "", ""))

val searchResult = SearchResult("word1", meanings)

val searchResultList = listOf(
    SearchResult("word1", meanings),
    SearchResult("word2", meanings)
)
