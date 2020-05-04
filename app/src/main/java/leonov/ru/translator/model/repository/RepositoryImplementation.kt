package leonov.ru.translator.model.repository

import leonov.ru.translator.model.data.SearchResult
import leonov.ru.translator.model.datasource.DataSource
import io.reactivex.rxjava3.core.Observable

class RepositoryImplementation(private val dataSource: DataSource<List<SearchResult>>) :
    Repository<List<SearchResult>> {

    override fun getData(word: String): Observable<List<SearchResult>> {
        return dataSource.getData(word)
    }
}
