package ru.leonov.repository.api


import kotlinx.coroutines.Deferred
import leonov.ru.model.data.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    fun searchAsync(@Query("search") wordToSearch: String): Deferred<List<SearchResult>>
}
