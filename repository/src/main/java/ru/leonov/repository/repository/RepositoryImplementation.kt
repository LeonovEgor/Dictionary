package ru.leonov.repository.repository

import leonov.ru.model.data.SearchResult
import ru.leonov.repository.datasource.DataSource

open class RepositoryImplementation(private val dataSource: DataSource<List<SearchResult>>): Repository<List<SearchResult>> {

    override suspend fun getData(word: String): List<SearchResult> = dataSource.getData(word)

}