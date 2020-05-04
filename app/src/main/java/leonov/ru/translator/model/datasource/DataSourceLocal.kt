package leonov.ru.translator.model.datasource

import leonov.ru.translator.model.data.SearchResult
import io.reactivex.rxjava3.core.Observable

class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    DataSource<List<SearchResult>> {

    override fun getData(word: String): Observable<List<SearchResult>> = remoteProvider.getData(word)
}
