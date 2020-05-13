package leonov.ru.translator.model.datasource

import io.reactivex.rxjava3.core.Observable
import leonov.ru.translator.model.data.SearchResult

class RoomDataBaseImplementation : DataSource<List<SearchResult>> {

    override fun getData(word: String): Observable<List<SearchResult>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
