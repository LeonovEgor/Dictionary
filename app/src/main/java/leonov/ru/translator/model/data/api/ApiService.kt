package leonov.ru.translator.model.data.api


import io.reactivex.rxjava3.core.Observable
import leonov.ru.translator.model.data.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Observable<List<SearchResult>>
}
