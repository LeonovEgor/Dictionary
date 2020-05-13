package leonov.ru.translator.utils

import leonov.ru.translator.model.data.DataModel
import leonov.ru.translator.model.data.Meanings
import leonov.ru.translator.model.data.SearchResult

fun parseSearchResults(data: DataModel): DataModel {
    val newSearchResults = arrayListOf<SearchResult>()
    when (data) {
        is DataModel.Success -> {
            val searchResults = data.data
            if (!searchResults.isNullOrEmpty()) {
                for (searchResult in searchResults) {
                    parseResult(searchResult, newSearchResults)
                }
            }
        }
    }

    return DataModel.Success(newSearchResults)
}

//TODO: переписать. Не хватает проверки остальных полей
private fun parseResult(searchResult: SearchResult, newSearchResults: ArrayList<SearchResult>) {
    if (!searchResult.text.isNullOrBlank() && !searchResult.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meanings>()
        for (meaning in searchResult.meanings) {
            if (meaning.translation != null && !meaning.translation.text.isNullOrBlank()) {
                newMeanings.add(Meanings(
                    meaning.translation,
                    meaning.partOfSpeechCode,
                    meaning.previewUrl,
                    meaning.transcription,
                    meaning.soundUrl,
                    meaning.imageUrl ))
            }
        }
        if (newMeanings.isNotEmpty()) {
            newSearchResults.add(SearchResult(searchResult.text, newMeanings))
        }
    }
}

fun convertMeaningsToString(meanings: List<Meanings>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translation?.text, ", ")
        } else {
            meaning.translation?.text
        }
    }
    return meaningsSeparatedByComma
}