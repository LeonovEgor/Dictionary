package leonov.ru.translator.model.datasource

import leonov.ru.translator.model.data.SearchResult
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Observable<List<SearchResult>>
}
