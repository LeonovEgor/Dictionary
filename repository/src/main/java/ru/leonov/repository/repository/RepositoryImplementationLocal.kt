package ru.leonov.repository.repository

import ru.leonov.repository.datasource.DataSourceLocal
import ru.leonov.repository.room.HistoryEntity

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<HistoryEntity>>) :
    RepositoryLocal<List<HistoryEntity>> {

    override suspend fun getData(word: String): List<HistoryEntity> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(history: HistoryEntity) {
        dataSource.saveToDB(history)
    }
}
