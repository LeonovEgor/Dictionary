package leonov.ru.translator.model.data.api


import kotlinx.coroutines.Deferred
import leonov.ru.translator.model.data.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    fun searchAsync(@Query("search") wordToSearch: String): Deferred<List<SearchResult>>
}
