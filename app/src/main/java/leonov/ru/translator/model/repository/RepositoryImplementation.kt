package leonov.ru.translator.model.repository

import leonov.ru.translator.model.data.SearchResult
import leonov.ru.translator.model.datasource.DataSource

open class RepositoryImplementation(private val dataSource: DataSource<List<SearchResult>>): Repository<List<SearchResult>> {

    override suspend fun getData(word: String): List<SearchResult> = dataSource.getData(word)

}