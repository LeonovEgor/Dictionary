package leonov.ru.translator.model.datasource

import leonov.ru.translator.model.data.SearchResult
import io.reactivex.rxjava3.core.Observable

class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    DataSource<List<SearchResult>> {

    override fun getData(word: String): Observable<List<SearchResult>> = remoteProvider.getData(word)
}
